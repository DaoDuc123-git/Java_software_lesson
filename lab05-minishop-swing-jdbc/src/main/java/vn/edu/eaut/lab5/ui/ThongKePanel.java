package vn.edu.eaut.lab5.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import vn.edu.eaut.lab5.bus.ThongKeBUS;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.util.MessageUtil;

public class ThongKePanel extends JPanel {
    private final ThongKeBUS thongKeBUS = new ThongKeBUS();
    private JTextField txtTuNgay, txtDenNgay;
    private JLabel lblDoanhThu, lblHoaDonCaoNhat, lblSanPhamBanChay;

    public ThongKePanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        JPanel pnlFilter = new JPanel(new FlowLayout());
        pnlFilter.setBorder(BorderFactory.createTitledBorder("Loc doanh thu theo ngay (YYYY-MM-DD)"));

        txtTuNgay = new JTextField(LocalDate.now().withDayOfMonth(1).toString(), 10);
        txtDenNgay = new JTextField(LocalDate.now().toString(), 10);
        JButton btnThongKe = new JButton("Thong Ke (SwingWorker)");

        pnlFilter.add(new JLabel("Tu ngay:"));
        pnlFilter.add(txtTuNgay);
        pnlFilter.add(new JLabel("Den ngay:"));
        pnlFilter.add(txtDenNgay);
        pnlFilter.add(btnThongKe);

        add(pnlFilter, BorderLayout.NORTH);

        JPanel pnlResult = new JPanel(new GridLayout(3, 1, 10, 10));
        pnlResult.setBorder(BorderFactory.createTitledBorder("Ket qua thong ke"));

        lblDoanhThu = new JLabel("Doanh thu: 0 VND");
        lblHoaDonCaoNhat = new JLabel("Hoa don cao nhat: Chưa co dữ liệu");
        lblSanPhamBanChay = new JLabel("San pham ban chay nhất: Chưa co dữ liệu");

        Font font = new Font("Arial", Font.BOLD, 13);
        lblDoanhThu.setFont(font);
        lblHoaDonCaoNhat.setFont(font);
        lblSanPhamBanChay.setFont(font);

        pnlResult.add(lblDoanhThu);
        pnlResult.add(lblHoaDonCaoNhat);
        pnlResult.add(lblSanPhamBanChay);

        add(pnlResult, BorderLayout.CENTER);

        btnThongKe.addActionListener(e -> runThongKeWorker());
    }

    private void runThongKeWorker() {
        try {
            LocalDate tuNgay = LocalDate.parse(txtTuNgay.getText().trim());
            LocalDate denNgay = LocalDate.parse(txtDenNgay.getText().trim());

            btnThongKeDisabled(true);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                private BigDecimal doanhThu;
                private HoaDon hdCaoNhat;
                private String spBanChay;

                @Override
                protected Void doInBackground() throws Exception {
                    doanhThu = thongKeBUS.tinhDoanhThu(tuNgay, denNgay);
                    hdCaoNhat = thongKeBUS.getHoaDonCaoNhat();
                    spBanChay = thongKeBUS.getSanPhamBanChayNhat();
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        get();
                        lblDoanhThu.setText("Doanh thu tu " + tuNgay + " den " + denNgay + ": " + doanhThu + " VND");
                        if (hdCaoNhat != null) {
                            lblHoaDonCaoNhat.setText("Hoa don cao nhat: Ma HD " + hdCaoNhat.getMaHd() + " - " + hdCaoNhat.getTenKh() + " (" + hdCaoNhat.getTongTien() + " VND)");
                        } else {
                            lblHoaDonCaoNhat.setText("Hoa don cao nhat: Khong co du lieu");
                        }
                        lblSanPhamBanChay.setText("San pham ban chay nhat: " + spBanChay);
                    } catch (Exception ex) {
                        MessageUtil.showError("Loi thong ke: " + ex.getMessage());
                    } finally {
                        btnThongKeDisabled(false);
                    }
                }
            };

            worker.execute();
        } catch (Exception e) {
            MessageUtil.showError("Dinh dang ngay khong hop le (YYYY-MM-DD)");
            btnThongKeDisabled(false);
        }
    }

    private void btnThongKeDisabled(boolean disabled) {
        lblDoanhThu.setText(disabled ? "Dang tinh toan..." : lblDoanhThu.getText());
    }
}