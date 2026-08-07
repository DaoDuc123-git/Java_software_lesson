package vn.edu.eaut.lab3;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// Class Model SinhVien tách riêng dữ liệu
class SinhVien {
    private String maSV;
    private String hoTen;
    private double diem;

    public SinhVien(String maSV, String hoTen, double diem) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.diem = diem;
    }

    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public double getDiem() { return diem; }
    public void setDiem(double diem) { this.diem = diem; }

    public String getXepLoai() {
        if (diem >= 8.5) return "Xuất sắc";
        if (diem >= 7.0) return "Khá";
        if (diem >= 5.0) return "Trung bình";
        return "Yếu";
    }
}

// Class Giao diện
public class Bai08QuanLySinhVien extends JFrame {
    private JTextField txtMaSV, txtHoTen, txtDiem;
    private JTable tblSinhVien;
    private DefaultTableModel tableModel;
    private List<SinhVien> listSV = new ArrayList<>();

    public Bai08QuanLySinhVien() {
        setTitle("Bài 8 - Quản Lý Sinh Viên");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Input Form
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        
        panelInput.add(new JLabel("Mã SV:"));
        txtMaSV = new JTextField();
        panelInput.add(txtMaSV);

        panelInput.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        panelInput.add(txtHoTen);

        panelInput.add(new JLabel("Điểm:"));
        txtDiem = new JTextField();
        panelInput.add(txtDiem);

        add(panelInput, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Mã SV", "Họ tên", "Điểm", "Xếp loại"}, 0);
        tblSinhVien = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(tblSinhVien);
        add(scrollTable, BorderLayout.CENTER);

        // Buttons
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        panelBtns.add(btnThem);
        panelBtns.add(btnSua);
        panelBtns.add(btnXoa);
        panelBtns.add(btnLamMoi);
        add(panelBtns, BorderLayout.SOUTH);

        // Actions
        btnThem.addActionListener(e -> xuLyThem());
        btnSua.addActionListener(e -> xuLySua());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnLamMoi.addActionListener(e -> xoaForm());

        tblSinhVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblSinhVien.getSelectedRow();
                if (selectedRow >= 0) {
                    txtMaSV.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtHoTen.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtDiem.setText(tableModel.getValueAt(selectedRow, 2).toString());
                }
            }
        });
    }

    private void xuLyThem() {
        try {
            String ma = txtMaSV.getText().trim();
            String ten = txtHoTen.getText().trim();
            double diem = Double.parseDouble(txtDiem.getText().trim());

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã SV và Họ tên không được để trống!");
                return;
            }

            SinhVien sv = new SinhVien(ma, ten, diem);
            listSV.add(sv);
            tableModel.addRow(new Object[]{sv.getMaSV(), sv.getHoTen(), sv.getDiem(), sv.getXepLoai()});
            xoaForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm số không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLySua() {
        int row = tblSinhVien.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn 1 dòng trong bảng để sửa!");
            return;
        }

        try {
            String ma = txtMaSV.getText().trim();
            String ten = txtHoTen.getText().trim();
            double diem = Double.parseDouble(txtDiem.getText().trim());

            SinhVien sv = listSV.get(row);
            sv.setMaSV(ma);
            sv.setHoTen(ten);
            sv.setDiem(diem);

            tableModel.setValueAt(ma, row, 0);
            tableModel.setValueAt(ten, row, 1);
            tableModel.setValueAt(diem, row, 2);
            tableModel.setValueAt(sv.getXepLoai(), row, 3);

            xoaForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm số không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyXoa() {
        int row = tblSinhVien.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn 1 dòng trong bảng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            listSV.remove(row);
            tableModel.removeRow(row);
            xoaForm();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
        }
    }

    private void xoaForm() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtDiem.setText("");
        tblSinhVien.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai08QuanLySinhVien().setVisible(true));
    }
}