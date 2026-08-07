package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Bai07MayTinhMini extends JFrame {
    private JTextField txtSoA, txtSoB, txtKetQua;
    private JTextArea txtLichSu;

    public Bai07MayTinhMini() {
        setTitle("Bài 7 - Máy Tính Mini");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form nhập
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createTitledBorder("Nhập dữ liệu"));
        
        panelInput.add(new JLabel("Số a:"));
        txtSoA = new JTextField();
        panelInput.add(txtSoA);

        panelInput.add(new JLabel("Số b:"));
        txtSoB = new JTextField();
        panelInput.add(txtSoB);

        panelInput.add(new JLabel("Kết quả:"));
        txtKetQua = new JTextField();
        txtKetQua.setEditable(false);
        panelInput.add(txtKetQua);

        add(panelInput, BorderLayout.NORTH);

        // Các nút phép tính
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnCong = new JButton("+");
        JButton btnTru = new JButton("-");
        JButton btnNhan = new JButton("*");
        JButton btnChia = new JButton("/");
        JButton btnClear = new JButton("Clear");

        panelBtns.add(btnCong);
        panelBtns.add(btnTru);
        panelBtns.add(btnNhan);
        panelBtns.add(btnChia);
        panelBtns.add(btnClear);
        add(panelBtns, BorderLayout.CENTER);

        // Lịch sử
        txtLichSu = new JTextArea(8, 30);
        txtLichSu.setEditable(false);
        JScrollPane scrollHistory = new JScrollPane(txtLichSu);
        scrollHistory.setBorder(BorderFactory.createTitledBorder("Lịch sử tính toán"));
        add(scrollHistory, BorderLayout.SOUTH);

        // Event listener
        btnCong.addActionListener(e -> xuLyPhepTinh("+"));
        btnTru.addActionListener(e -> xuLyPhepTinh("-"));
        btnNhan.addActionListener(e -> xuLyPhepTinh("*"));
        btnChia.addActionListener(e -> xuLyPhepTinh("/"));
        btnClear.addActionListener(this::xuLyClear);
    }

    private void xuLyPhepTinh(String pt) {
        try {
            double a = Double.parseDouble(txtSoA.getText().trim());
            double b = Double.parseDouble(txtSoB.getText().trim());
            double kq = 0;

            if (pt.equals("/") && b == 0) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không thể chia cho 0!", 
                        "Lỗi toán học", JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (pt) {
                case "+": kq = a + b; break;
                case "-": kq = a - b; break;
                case "*": kq = a * b; break;
                case "/": kq = a / b; break;
            }

            txtKetQua.setText(String.valueOf(kq));
            String log = String.format("%.2f %s %.2f = %.2f\n", a, pt, b, kq);
            txtLichSu.append(log);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", 
                    "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyClear(ActionEvent e) {
        txtSoA.setText("");
        txtSoB.setText("");
        txtKetQua.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai07MayTinhMini().setVisible(true));
    }
}