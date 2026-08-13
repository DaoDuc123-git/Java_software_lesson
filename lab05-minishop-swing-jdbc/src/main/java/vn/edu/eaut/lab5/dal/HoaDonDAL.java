package vn.edu.eaut.lab5.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;

public class HoaDonDAL {
    public int insertHoaDon(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        String sqlHoaDon = "INSERT INTO hoa_don(ngay_lap, ma_kh, tong_tien) VALUES (?, ?, ?)";
        String sqlChiTiet = "INSERT INTO chi_tiet_hoa_don(ma_hd, ma_sp, so_luong, don_gia, thanh_tien) VALUES (?, ?, ?, ?, ?)";
        String sqlTruTonKho = "UPDATE san_pham SET so_luong = so_luong - ? WHERE ma_sp = ?";

        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
            conn.setAutoCommit(false);

            BigDecimal tongTien = BigDecimal.ZERO;
            for (ChiTietHoaDon ct : chiTietList) {
                tongTien = tongTien.add(ct.getThanhTien());
            }

            int maHd = 0;
            try (PreparedStatement ps = conn.prepareStatement(sqlHoaDon, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, Date.valueOf(LocalDate.now()));
                ps.setInt(2, maKh);
                ps.setBigDecimal(3, tongTien);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        maHd = rs.getInt(1);
                    } else {
                        throw new SQLException("Khong lay duoc ma hoa don");
                    }
                }
            }

            try (PreparedStatement psChiTiet = conn.prepareStatement(sqlChiTiet);
                 PreparedStatement psKho = conn.prepareStatement(sqlTruTonKho)) {
                for (ChiTietHoaDon ct : chiTietList) {
                    psChiTiet.setInt(1, maHd);
                    psChiTiet.setInt(2, ct.getMaSp());
                    psChiTiet.setInt(3, ct.getSoLuong());
                    psChiTiet.setBigDecimal(4, ct.getDonGia());
                    psChiTiet.setBigDecimal(5, ct.getThanhTien());
                    psChiTiet.addBatch();

                    psKho.setInt(1, ct.getSoLuong());
                    psKho.setInt(2, ct.getMaSp());
                    psKho.addBatch();
                }
                psChiTiet.executeBatch();
                psKho.executeBatch();
            }

            conn.commit();
            return maHd;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}