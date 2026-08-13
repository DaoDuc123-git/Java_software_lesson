package vn.edu.eaut.lab5.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

public class SanPhamPanel extends JPanel {
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private JTextField txtMaSp, txtTenSp, txtDonGia, txtSoLuong, txtTimKiem;
    private JTable tblSanPham;
    private DefaultTableModel tableModel;

    public SanPhamPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thong tin san pham"));

        txtMaSp = new JTextField();
        txtMaSp.setEditable(false);
        txtTenSp = new JTextField();
        txtDonGia = new JTextField();
        txtSoLuong = new JTextField();
        txtTimKiem = new JTextField();

        pnlForm.add(new JLabel("Ma SP:"));
        pnlForm.add(txtMaSp);
        pnlForm.add(new JLabel("Ten SP:"));
        pnlForm.add(txtTenSp);
        pnlForm.add(new JLabel("Don gia:"));
        pnlForm.add(txtDonGia);
        pnlForm.add(new JLabel("So luong:"));
        pnlForm.add(txtSoLuong);
        pnlForm.add(new JLabel("Tim kiem (Ten):"));
        pnlForm.add(txtTimKiem);

        add(pnlForm, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Ma SP", "Ten SP", "Don Gia", "So Luong"}, 0);
        tblSanPham = new JTable(tableModel);
        add(new JScrollPane(tblSanPham), BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout());
        JButton btnThem = new JButton("Them");
        JButton btnSua = new JButton("Sua");
        JButton btnXoa = new JButton("Xoa");
        JButton btnLamMoi = new JButton("Lam moi");
        JButton btnTimKiem = new JButton("Tim kiem");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);
        pnlButtons.add(btnTimKiem);
        add(pnlButtons, BorderLayout.SOUTH);

        tblSanPham.getSelectionModel().addListSelectionListener(e -> {
            int row = tblSanPham.getSelectedRow();
            if (row >= 0) {
                txtMaSp.setText(tableModel.getValueAt(row, 0).toString());
                txtTenSp.setText(tableModel.getValueAt(row, 1).toString());
                txtDonGia.setText(tableModel.getValueAt(row, 2).toString());
                txtSoLuong.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        btnThem.addActionListener(e -> saveSanPham(true));
        btnSua.addActionListener(e -> saveSanPham(false));
        btnXoa.addActionListener(e -> deleteSanPham());
        btnLamMoi.addActionListener(e -> clearForm());
        btnTimKiem.addActionListener(e -> searchSanPham());
    }

    public void loadData() {
        try {
            List<SanPham> list = sanPhamBUS.findAll();
            fillTable(list);
        } catch (Exception e) {
            MessageUtil.showError("Loi tai du lieu: " + e.getMessage());
        }
    }

    private void fillTable(List<SanPham> list) {
        tableModel.setRowCount(0);
        for (SanPham sp : list) {
            tableModel.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getDonGia(), sp.getSoLuong()});
        }
    }

    private void saveSanPham(boolean isNew) {
        try {
            SanPham sp = new SanPham();
            if (!isNew) {
                if (txtMaSp.getText().isEmpty()) {
                    MessageUtil.showError("Vui long chon san pham de sua");
                    return;
                }
                sp.setMaSp(Integer.parseInt(txtMaSp.getText()));
            }
            sp.setTenSp(txtTenSp.getText());
            sp.setDonGia(new BigDecimal(txtDonGia.getText().trim()));
            sp.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));

            if (sanPhamBUS.save(sp)) {
                MessageUtil.showInfo("Luu thanh cong!");
                clearForm();
                loadData();
            }
        } catch (Exception e) {
            MessageUtil.showError("Loi: " + e.getMessage());
        }
    }

    private void deleteSanPham() {
        if (txtMaSp.getText().isEmpty()) {
            MessageUtil.showError("Vui long chon san pham de xoa");
            return;
        }
        if (MessageUtil.showConfirm("Ban co chac chan muon xoa?")) {
            try {
                int maSp = Integer.parseInt(txtMaSp.getText());
                if (sanPhamBUS.delete(maSp)) {
                    MessageUtil.showInfo("Xoa thanh cong!");
                    clearForm();
                    loadData();
                }
            } catch (Exception e) {
                MessageUtil.showError("Loi xoa: " + e.getMessage());
            }
        }
    }

    private void searchSanPham() {
        try {
            List<SanPham> list = sanPhamBUS.searchByName(txtTimKiem.getText().trim());
            fillTable(list);
        } catch (Exception e) {
            MessageUtil.showError("Loi tim kiem: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        txtTimKiem.setText("");
        loadData();
    }
}