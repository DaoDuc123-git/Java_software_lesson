package vn.edu.eaut.lab4;
import javax.swing.*;
import java.awt.*;

public class PrimeSumFrame extends JFrame {

    private JTextField txtN;
    private JButton btnCalculate;
    private JLabel lblResult;
    private JProgressBar progressBar;

    public PrimeSumFrame() {

        setTitle("Bài 3 - Tổng các số nguyên tố");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ô nhập N
        txtN = new JTextField();

        // Nút Tính
        btnCalculate = new JButton("Tính");

        // Kết quả
        lblResult = new JLabel(
                "Nhập N rồi nhấn Tính",
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
        panel.add(btnCalculate);
        panel.add(progressBar);
        panel.add(lblResult);

        add(panel);

        // Xử lý nút Tính
        btnCalculate.addActionListener(
                e -> calculatePrimeSum()
        );
    }

    // Kiểm tra số nguyên tố
    private boolean isPrime(int n) {

        if (n < 2) {
            return false;
        }

        if (n == 2) {
            return true;
        }

        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(n); i += 2) {

            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    // Tính tổng số nguyên tố
    private void calculatePrimeSum() {

        int n;

        // Kiểm tra dữ liệu nhập
        try {

            n = Integer.parseInt(
                    txtN.getText().trim()
            );

            if (n <= 2) {

                JOptionPane.showMessageDialog(
                        this,
                        "N phải lớn hơn 2"
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

        // Khóa nút trong khi tính
        btnCalculate.setEnabled(false);
        txtN.setEnabled(false);

        progressBar.setValue(0);

        lblResult.setText("Đang tính...");

        // SwingWorker chạy tác vụ nền
        SwingWorker<Long, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Long doInBackground() {

                        long sum = 0;

                        for (int i = 2; i < n; i++) {

                            // Nếu là số nguyên tố thì cộng vào tổng
                            if (isPrime(i)) {
                                sum += i;
                            }

                            // Tính phần trăm
                            int progress =
                                    (int) ((i * 100.0) / n);

                            setProgress(progress);
                        }

                        return sum;
                    }

                    @Override
                    protected void done() {

                        try {

                            long result = get();

                            lblResult.setText(
                                    "Tổng các số nguyên tố nhỏ hơn "
                                            + n + " = " + result
                            );

                        } catch (Exception ex) {

                            lblResult.setText(
                                    "Có lỗi khi tính toán"
                            );
                        }

                        progressBar.setValue(100);

                        btnCalculate.setEnabled(true);
                        txtN.setEnabled(true);
                    }
                };

        // Cập nhật ProgressBar
        worker.addPropertyChangeListener(evt -> {

            if ("progress".equals(
                    evt.getPropertyName())) {

                int progress =
                        (int) evt.getNewValue();

                progressBar.setValue(progress);
            }
        });

        // Chạy SwingWorker
        worker.execute();
    }
}