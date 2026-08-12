package vn.edu.eaut.lab4;
import javax.swing.*;
import java.awt.*;

public class ProgressDemoFrame extends JFrame {

    private JButton btnLoad;
    private JProgressBar progressBar;
    private JLabel lblStatus;

    public ProgressDemoFrame() {

        setTitle("Bài 2 - Mô phỏng tải dữ liệu");
        setSize(450, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Nút tải dữ liệu
        btnLoad = new JButton("Tải dữ liệu");

        // Thanh tiến trình
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Trạng thái
        lblStatus = new JLabel(
                "Chưa tải dữ liệu",
                SwingConstants.CENTER
        );

        // Panel
        JPanel panel = new JPanel(
                new GridLayout(3, 1, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        panel.add(btnLoad);
        panel.add(progressBar);
        panel.add(lblStatus);

        add(panel);

        // Xử lý sự kiện
        btnLoad.addActionListener(e -> loadData());
    }

    private void loadData() {

        // Khóa nút khi đang tải
        btnLoad.setEnabled(false);

        // Reset tiến trình
        progressBar.setValue(0);

        // Hiển thị trạng thái
        lblStatus.setText("Đang tải dữ liệu...");

        // Tạo SwingWorker
        SwingWorker<Void, Integer> worker =
                new SwingWorker<>() {

                    @Override
                    protected Void doInBackground() throws Exception {

                        // Mô phỏng tải dữ liệu trong 10 giây
                        for (int i = 0; i <= 100; i += 10) {

                            setProgress(i);

                            Thread.sleep(1000);
                        }

                        return null;
                    }

                    @Override
                    protected void done() {

                        // Hoàn thành
                        progressBar.setValue(100);

                        lblStatus.setText(
                                "Tải dữ liệu hoàn tất"
                        );

                        btnLoad.setEnabled(true);
                    }
                };

        // Lắng nghe tiến trình
        worker.addPropertyChangeListener(evt -> {

            if ("progress".equals(evt.getPropertyName())) {

                int progress =
                        (int) evt.getNewValue();

                progressBar.setValue(progress);

                lblStatus.setText(
                        "Đang tải: " + progress + "%"
                );
            }
        });

        // Bắt đầu chạy nền
        worker.execute();
    }
}