package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileLineCounterFrame extends JFrame {

    private JButton btnChoose;
    private JButton btnCount;
    private JLabel lblFile;
    private JLabel lblResult;
    private JProgressBar progressBar;

    private File selectedFile;

    public FileLineCounterFrame() {

        setTitle("Bài 5 - Đọc file và đếm số dòng");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Nút chọn file
        btnChoose = new JButton("Chọn file");

        // Nút đếm dòng
        btnCount = new JButton("Đếm dòng");

        // Hiển thị file
        lblFile = new JLabel("Chưa chọn file");

        // Hiển thị kết quả
        lblResult = new JLabel(
                "Kết quả: ",
                SwingConstants.CENTER
        );

        // Thanh tiến trình
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Panel
        JPanel panel = new JPanel(
                new GridLayout(5, 1, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

        panel.add(btnChoose);
        panel.add(lblFile);
        panel.add(btnCount);
        panel.add(progressBar);
        panel.add(lblResult);

        add(panel);

        // Sự kiện chọn file
        btnChoose.addActionListener(
                e -> chooseFile()
        );

        // Sự kiện đếm dòng
        btnCount.addActionListener(
                e -> countLines()
        );
    }

    // Chọn file
    private void chooseFile() {

        JFileChooser chooser =
                new JFileChooser();

        int result =
                chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            selectedFile =
                    chooser.getSelectedFile();

            lblFile.setText(
                    "File: "
                            + selectedFile.getAbsolutePath()
            );

            lblResult.setText(
                    "Kết quả: Chưa đếm"
            );

            progressBar.setValue(0);
        }
    }

    // Đếm số dòng
    private void countLines() {

        // Kiểm tra đã chọn file chưa
        if (selectedFile == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file trước"
            );

            return;
        }

        // Khóa nút khi đang xử lý
        btnChoose.setEnabled(false);
        btnCount.setEnabled(false);

        progressBar.setValue(0);

        lblResult.setText(
                "Đang đọc file..."
        );

        // Tạo SwingWorker
        SwingWorker<Long, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Long doInBackground()
                            throws Exception {

                        // Tổng dung lượng file
                        long totalBytes =
                                Files.size(
                                        selectedFile.toPath()
                                );

                        long readBytes = 0;
                        long lines = 0;

                        try (
                                BufferedReader reader =
                                        Files.newBufferedReader(
                                                selectedFile.toPath(),
                                                StandardCharsets.UTF_8
                                        )
                        ) {

                            String line;

                            while (
                                    (line = reader.readLine())
                                            != null
                            ) {

                                lines++;

                                // Ước lượng số byte đã đọc
                                readBytes +=
                                        line.getBytes(
                                                StandardCharsets.UTF_8
                                        ).length + 1;

                                int progress;

                                if (totalBytes == 0) {
                                    progress = 100;
                                } else {
                                    progress =
                                            (int) Math.min(
                                                    100,
                                                    readBytes * 100
                                                            / totalBytes
                                            );
                                }

                                setProgress(progress);
                            }
                        }

                        return lines;
                    }

                    @Override
                    protected void done() {

                        try {

                            long lineCount = get();

                            lblResult.setText(
                                    "Số dòng: "
                                            + lineCount
                            );

                        } catch (Exception ex) {

                            lblResult.setText(
                                    "Lỗi khi đọc file"
                            );
                        }

                        progressBar.setValue(100);

                        btnChoose.setEnabled(true);
                        btnCount.setEnabled(true);
                    }
                };

        // Cập nhật ProgressBar
        worker.addPropertyChangeListener(
                evt -> {

                    if ("progress".equals(
                            evt.getPropertyName()
                    )) {

                        int progress =
                                (int) evt.getNewValue();

                        progressBar.setValue(
                                progress
                        );
                    }
                }
        );

        // Chạy SwingWorker
        worker.execute();
    }
}