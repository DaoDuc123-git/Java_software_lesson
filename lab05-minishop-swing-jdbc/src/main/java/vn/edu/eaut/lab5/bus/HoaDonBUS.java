package vn.edu.eaut.lab5.bus;

import java.sql.SQLException;
import java.util.List;

import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;

public class HoaDonBUS {
    private final HoaDonDAL hoaDonDAL = new HoaDonDAL();

    public int lapHoaDon(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Vui long chon khach hang!");
        }
        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new IllegalArgumentException("Danh sach chi tiet hoa don khong duoc de rong!");
        }
        return hoaDonDAL.insertHoaDon(maKh, chiTietList);
    }
}