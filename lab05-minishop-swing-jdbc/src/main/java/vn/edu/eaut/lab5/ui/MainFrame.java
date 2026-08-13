package vn.edu.eaut.lab5.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("HE THONG QUAN LY BAN HANG MINISHOP");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        SanPhamPanel sanPhamPanel = new SanPhamPanel();
        KhachHangPanel khachHangPanel = new KhachHangPanel();
        HoaDonPanel hoaDonPanel = new HoaDonPanel();
        ThongKePanel thongKePanel = new ThongKePanel();

        tabbedPane.addTab("San Pham", sanPhamPanel);
        tabbedPane.addTab("Khach Hang", khachHangPanel);
        tabbedPane.addTab("Hoa Don", hoaDonPanel);
        tabbedPane.addTab("Thong Ke", thongKePanel);

        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 2) {
                hoaDonPanel.loadComboboxData();
            } else if (selectedIndex == 0) {
                sanPhamPanel.loadData();
            } else if (selectedIndex == 1) {
                khachHangPanel.loadData();
            }
        });

        add(tabbedPane);
    }
}