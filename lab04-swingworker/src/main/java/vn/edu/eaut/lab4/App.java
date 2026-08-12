package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    public App() {

        setTitle("LAB 4 - JAVA SWING & SWINGWORKER");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(
                "LAB 4 - XỬ LÝ SỰ KIỆN & SWINGWORKER",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        JPanel panel = new JPanel(
                new GridLayout(10, 1, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 30, 15, 30
                )
        );

        JButton btn1 =
                new JButton("Bài 1 - Đồng hồ đếm ngược");

        JButton btn2 =
                new JButton("Bài 2 - Mô phỏng tải dữ liệu");

        JButton btn3 =
                new JButton("Bài 3 - Tổng số nguyên tố");

        JButton btn4 =
                new JButton("Bài 4 - Fibonacci");

        JButton btn5 =
                new JButton("Bài 5 - Đếm số dòng file");

       

        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);
        panel.add(btn5);
       

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btn1.addActionListener(e ->
                new CountdownFrame().setVisible(true)
        );

        btn2.addActionListener(e ->
                new ProgressDemoFrame().setVisible(true)
        );

        btn3.addActionListener(e ->
                new PrimeSumFrame().setVisible(true)
        );

        btn4.addActionListener(e ->
                new FibonacciFrame().setVisible(true)
        );

        btn5.addActionListener(e ->
                new FileLineCounterFrame().setVisible(true)
        );

      
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }
}

