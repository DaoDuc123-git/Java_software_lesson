package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; // <-- Bổ sung dòng này

public class Bai06LoginForm extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JComboBox<String> cboRole;
    private JCheckBox chkRemember;

    public Bai06LoginForm() {
        setTitle("Bài 6 - Form Đăng Nhập");
        setSize(380, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên đăng nhập
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        txtUser = new JTextField(15);
        add(txtUser, gbc);

        // Mật khẩu
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtPass = new JPasswordField(15);
        add(txtPass, gbc);

        // Vai trò
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 1;
        cboRole = new JComboBox<>(new String[]{"Sinh viên", "Giảng viên", "Quản trị viên"});
        add(cboRole, gbc);

        // Ghi nhớ
        gbc.gridx = 1; gbc.gridy = 3;
        chkRemember = new JCheckBox("Ghi nhớ đăng nhập");
        add(chkRemember, gbc);

        // Nút Đăng nhập
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton btnLogin = new JButton("Đăng nhập");
        add(btnLogin, gbc);

        btnLogin.addActionListener(this::xuLyDangNhap);
    }

    private void xuLyDangNhap(ActionEvent e) {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());
        String role = (String) cboRole.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Tên đăng nhập và Mật khẩu!", 
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("admin".equals(username) && "123456".equals(password)) {
            String msg = "Đăng nhập thành công!\n"
                       + "Vai trò: " + role + "\n"
                       + "Ghi nhớ: " + (chkRemember.isSelected() ? "Có" : "Không");
            JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Sai Tên đăng nhập hoặc Mật khẩu!", 
                    "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai06LoginForm().setVisible(true));
    }
}