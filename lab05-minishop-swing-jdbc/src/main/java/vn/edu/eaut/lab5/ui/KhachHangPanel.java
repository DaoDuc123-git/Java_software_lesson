package vn.edu.eaut.lab5.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.MessageUtil;

public class KhachHangPanel extends JPanel {
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private JTextField txtMaKh, txtTenKh, txtSdt, txtDiaChi, txtTimKiem;
    private JTable tblKhachHang;
    private DefaultTableModel tableModel;

    public KhachHangPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thong tin khach hang"));

        txtMaKh = new JTextField();
        txtMaKh.setEditable(false);
        txtTenKh = new JTextField();
        txtSdt = new JTextField();
        ((AbstractDocument) txtSdt.getDocument()).setDocumentFilter(new MessageUtil.PhoneDocumentFilter());
        txtDiaChi = new JTextField();
        txtTimKiem = new JTextField();

        pnlForm.add(new JLabel("Ma KH:"));
        pnlForm.add(txtMaKh);
        pnlForm.add(new JLabel("Ten KH:"));
        pnlForm.add(txtTenKh);
        pnlForm.add(new JLabel("SDT:"));
        pnlForm.add(txtSdt);
        pnlForm.add(new JLabel("Dia chi:"));
        pnlForm.add(txtDiaChi);
        pnlForm.add(new JLabel("Tim kiem (Ten):"));
        pnlForm.add(txtTimKiem);

        add(pnlForm, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Ma KH", "Ten KH", "SDT", "Dia Chi"}, 0);
        tblKhachHang = new JTable(tableModel);
        add(new JScrollPane(tblKhachHang), BorderLayout.CENTER);

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

        tblKhachHang.getSelectionModel().addListSelectionListener(e -> {
            int row = tblKhachHang.getSelectedRow();
            if (row >= 0) {
                txtMaKh.setText(tableModel.getValueAt(row, 0).toString());
                txtTenKh.setText(tableModel.getValueAt(row, 1).toString());
                txtSdt.setText(tableModel.getValueAt(row, 2).toString());
                txtDiaChi.setText(tableModel.getValueAt(row, 3) == null ? "" : tableModel.getValueAt(row, 3).toString());
            }
        });

        btnThem.addActionListener(e -> saveKhachHang(true));
        btnSua.addActionListener(e -> saveKhachHang(false));
        btnXoa.addActionListener(e -> deleteKhachHang());
        btnLamMoi.addActionListener(e -> clearForm());
        btnTimKiem.addActionListener(e -> searchKhachHang());
    }

    public void loadData() {
        try {
            List<KhachHang> list = khachHangBUS.findAll();
            fillTable(list);
        } catch (Exception e) {
            MessageUtil.showError("Loi tai du lieu: " + e.getMessage());
        }
    }

    private void fillTable(List<KhachHang> list) {
        tableModel.setRowCount(0);
        for (KhachHang kh : list) {
            tableModel.addRow(new Object[]{kh.getMaKh(), kh.getTenKh(), kh.getSdt(), kh.getDiaChi()});
        }
    }

    private void saveKhachHang(boolean isNew) {
        try {
            KhachHang kh = new KhachHang();
            if (!isNew) {
                if (txtMaKh.getText().isEmpty()) {
                    MessageUtil.showError("Vui long chon khach hang de sua");
                    return;
                }
                kh.setMaKh(Integer.parseInt(txtMaKh.getText()));
            }
            kh.setTenKh(txtTenKh.getText());
            kh.setSdt(txtSdt.getText().trim());
            kh.setDiaChi(txtDiaChi.getText());

            if (khachHangBUS.save(kh)) {
                MessageUtil.showInfo("Luu thanh cong!");
                clearForm();
                loadData();
            }
        } catch (Exception e) {
            MessageUtil.showError("Loi: " + e.getMessage());
        }
    }

    private void deleteKhachHang() {
        if (txtMaKh.getText().isEmpty()) {
            MessageUtil.showError("Vui long chon khach hang de xoa");
            return;
        }
        if (MessageUtil.showConfirm("Ban co chac chan muon xoa?")) {
            try {
                int maKh = Integer.parseInt(txtMaKh.getText());
                if (khachHangBUS.delete(maKh)) {
                    MessageUtil.showInfo("Xoa thanh cong!");
                    clearForm();
                    loadData();
                }
            } catch (Exception e) {
                MessageUtil.showError("Loi xoa: " + e.getMessage());
            }
        }
    }

    private void searchKhachHang() {
        try {
            List<KhachHang> list = khachHangBUS.searchByName(txtTimKiem.getText().trim());
            fillTable(list);
        } catch (Exception e) {
            MessageUtil.showError("Loi tim kiem: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        txtTimKiem.setText("");
        loadData();
    }
}