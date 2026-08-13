package vn.edu.eaut.lab5.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

public class HoaDonPanel extends JPanel {
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();

    private JComboBox<KhachHang> cbKhachHang;
    private JComboBox<SanPham> cbSanPham;
    private JTextField txtSoLuong;
    private JLabel lblTongTien;
    private JTable tblChiTiet;
    private DefaultTableModel tableModel;

    private final List<ChiTietHoaDon> chiTietList = new ArrayList<>();

    public HoaDonPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadComboboxData();
    }

    private void initComponents() {
        JPanel pnlForm = new JPanel(new GridLayout(3, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Lap hoa don ban hang"));

        cbKhachHang = new JComboBox<>();
        cbSanPham = new JComboBox<>();
        txtSoLuong = new JTextField("1");

        pnlForm.add(new JLabel("Chon Khach Hang:"));
        pnlForm.add(cbKhachHang);
        pnlForm.add(new JLabel("Chon San Pham:"));
        pnlForm.add(cbSanPham);
        pnlForm.add(new JLabel("So Luong Mua:"));
        pnlForm.add(txtSoLuong);

        add(pnlForm, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Ma SP", "Ten SP", "So Luong", "Don Gia", "Thanh Tien"}, 0);
        tblChiTiet = new JTable(tableModel);
        add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThemMon = new JButton("Them vao gio");
        JButton btnXoaMon = new JButton("Xoa khoi gio");
        JButton btnThanhToan = new JButton("Luu & In Hoa Don");

        pnlBtns.add(btnThemMon);
        pnlBtns.add(btnXoaMon);
        pnlBtns.add(btnThanhToan);

        lblTongTien = new JLabel("Tong tien: 0 VND");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setForeground(Color.RED);

        pnlBottom.add(pnlBtns, BorderLayout.WEST);
        pnlBottom.add(lblTongTien, BorderLayout.EAST);
        add(pnlBottom, BorderLayout.SOUTH);

        btnThemMon.addActionListener(e -> addGioHang());
        btnXoaMon.addActionListener(e -> removeGioHang());
        btnThanhToan.addActionListener(e -> luuHoaDon());
    }

    public void loadComboboxData() {
        try {
            cbKhachHang.removeAllItems();
            for (KhachHang kh : khachHangBUS.findAll()) {
                cbKhachHang.addItem(kh);
            }

            cbSanPham.removeAllItems();
            for (SanPham sp : sanPhamBUS.findAll()) {
                cbSanPham.addItem(sp);
            }
        } catch (Exception e) {
            MessageUtil.showError("Loi load danh sach: " + e.getMessage());
        }
    }

    private void addGioHang() {
        SanPham spSelected = (SanPham) cbSanPham.getSelectedItem();
        if (spSelected == null) return;

        try {
            int sl = Integer.parseInt(txtSoLuong.getText().trim());
            if (sl <= 0) {
                MessageUtil.showError("So luong phai lon hon 0");
                return;
            }
            if (sl > spSelected.getSoLuong()) {
                MessageUtil.showError("So luong ton kho khong du (Con: " + spSelected.getSoLuong() + ")");
                return;
            }

            boolean exist = false;
            for (ChiTietHoaDon ct : chiTietList) {
                if (ct.getMaSp() == spSelected.getMaSp()) {
                    if (ct.getSoLuong() + sl > spSelected.getSoLuong()) {
                        MessageUtil.showError("Tong so luong vuot qua ton kho!");
                        return;
                    }
                    ct.setSoLuong(ct.getSoLuong() + sl);
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                ChiTietHoaDon ct = new ChiTietHoaDon(spSelected.getMaSp(), spSelected.getTenSp(), sl, spSelected.getDonGia());
                chiTietList.add(ct);
            }

            updateTableAndTotal();
        } catch (NumberFormatException e) {
            MessageUtil.showError("So luong phai la so nguyen!");
        }
    }

    private void removeGioHang() {
        int row = tblChiTiet.getSelectedRow();
        if (row >= 0) {
            chiTietList.remove(row);
            updateTableAndTotal();
        } else {
            MessageUtil.showError("Chon dong can xoa khoi gio!");
        }
    }

    private void updateTableAndTotal() {
        tableModel.setRowCount(0);
        BigDecimal tongTien = BigDecimal.ZERO;
        for (ChiTietHoaDon ct : chiTietList) {
            tableModel.addRow(new Object[]{ct.getMaSp(), ct.getTenSp(), ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien()});
            tongTien = tongTien.add(ct.getThanhTien());
        }
        lblTongTien.setText("Tong tien: " + tongTien + " VND");
    }

    private void luuHoaDon() {
        KhachHang khSelected = (KhachHang) cbKhachHang.getSelectedItem();
        if (khSelected == null) {
            MessageUtil.showError("Vui long chon khach hang!");
            return;
        }
        if (chiTietList.isEmpty()) {
            MessageUtil.showError("Gio hang dang de rong!");
            return;
        }

        try {
            int maHd = hoaDonBUS.lapHoaDon(khSelected.getMaKh(), chiTietList);
            MessageUtil.showInfo("Lap hoa don thanh cong! Ma HD: " + maHd);
            chiTietList.clear();
            updateTableAndTotal();
            loadComboboxData();
        } catch (Exception e) {
            MessageUtil.showError("Loi luu hoa don: " + e.getMessage());
        }
    }
}