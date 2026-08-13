package vn.edu.eaut.lab5.bus;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import vn.edu.eaut.lab5.dal.ThongKeDAL;
import vn.edu.eaut.lab5.model.HoaDon;

public class ThongKeBUS {
    private final ThongKeDAL thongKeDAL = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        if (tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException("Tu ngay khong duoc lon hon den ngay");
        }
        return thongKeDAL.tinhDoanhThu(tuNgay, denNgay);
    }

    public HoaDon getHoaDonCaoNhat() throws SQLException {
        return thongKeDAL.getHoaDonCaoNhat();
    }

    public String getSanPhamBanChayNhat() throws SQLException {
        return thongKeDAL.getSanPhamBanChayNhat();
    }
}