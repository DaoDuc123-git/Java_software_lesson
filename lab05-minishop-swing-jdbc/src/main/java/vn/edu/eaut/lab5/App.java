package vn.edu.eaut.lab5;

import javax.swing.SwingUtilities;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.ui.MainFrame;

public class App {
    public static void main(String[] args) {
        DBHelper.testConnection();
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}