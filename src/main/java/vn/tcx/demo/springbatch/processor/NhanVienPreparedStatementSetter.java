package vn.tcx.demo.springbatch.processor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import vn.tcx.demo.springbatch.domain.NhanVien;

public class NhanVienPreparedStatementSetter implements ItemPreparedStatementSetter<NhanVien>{

    @Override
    public void setValues(NhanVien nhanVien, PreparedStatement ps) throws SQLException {
        
        ps.setString(1, nhanVien.getMaNhanVien());
        ps.setString(2, nhanVien.getTenNhanVien());
        ps.setString(3, nhanVien.getEmail());
        ps.setLong(4, nhanVien.getSoDienThoai());
    }

}
