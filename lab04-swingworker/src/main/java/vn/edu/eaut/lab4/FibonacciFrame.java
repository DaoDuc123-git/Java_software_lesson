package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class FibonacciFrame extends JFrame {

    private JTextField txtN;
    private JButton btnFind;
    private JLabel lblResult;
    private JProgressBar progressBar;

    public FibonacciFrame() {

        setTitle("Bài 4 - Tìm số Fibonacci");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ô nhập N
        txtN = new JTextField();

        // Nút Tìm
        btnFind = new JButton("Tìm");

        // Kết quả
        lblResult = new JLabel(
                "Nhập N rồi nhấn Tìm",
                SwingConstants.CENTER
        );

        // Thanh tiến trình
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Panel
        JPanel panel = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

        panel.add(txtN);
        panel.add(btnFind);
        panel.add(progressBar);
        panel.add(lblResult);

        add(panel);

        // Xử lý nút Tìm
        btnFind.addActionListener(
                e -> findFibonacci()
        );
    }

    // Hàm Fibonacci có Memoization
    private BigInteger fibonacci(
            int n,
            Map<Integer, BigInteger> memo) {

        if (n <= 1) {
            return BigInteger.valueOf(n);
        }

        // Nếu đã tính rồi thì lấy lại
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        // Tính Fibonacci
        BigInteger value =
                fibonacci(n - 1, memo)
                        .add(fibonacci(n - 2, memo));

        // Lưu kết quả
        memo.put(n, value);

        return value;
    }

    // Tìm Fibonacci
    private void findFibonacci() {

        int n;

        // Kiểm tra dữ liệu
        try {

            n = Integer.parseInt(
                    txtN.getText().trim()
            );

            if (n < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "N phải >= 0"
                );

                return;
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập số nguyên hợp lệ"
            );

            return;
        }

        // Khóa nút
        btnFind.setEnabled(false);
        txtN.setEnabled(false);

        // Hiển thị trạng thái đang tính
        progressBar.setIndeterminate(true);

        lblResult.setText(
                "Đang tính Fibonacci..."
        );

        // SwingWorker
        SwingWorker<BigInteger, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected BigInteger doInBackground() {

                        // Tạo bộ nhớ lưu kết quả
                        Map<Integer, BigInteger> memo =
                                new HashMap<>();

                        return fibonacci(n, memo);
                    }

                    @Override
                    protected void done() {

                        try {

                            BigInteger result = get();

                            lblResult.setText(
                                    "Fibonacci(" + n + ") = "
                                            + result
                            );

                        } catch (Exception ex) {

                            lblResult.setText(
                                    "Có lỗi khi tính Fibonacci"
                            );
                        }

                        // Kết thúc tiến trình
                        progressBar.setIndeterminate(false);
                        progressBar.setValue(100);

                        // Mở khóa
                        btnFind.setEnabled(true);
                        txtN.setEnabled(true);
                    }
                };

        // Chạy nền
        worker.execute();
    }
}