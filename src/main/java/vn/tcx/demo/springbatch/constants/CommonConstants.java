package vn.tcx.demo.springbatch.constants;

public class CommonConstants {

    public static final String DATA_SOURCE_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String DATA_SOURCE_URL = "jdbc:mysql://localhost:3306/springbatch";
    public static final String DATA_SOURCE_USER = "root";
    public static final String DATA_SOURCE_PASSWORD = "root";

    public static final String QUERY_INSERT_NHAN_VIEN = "INSERT INTO nhan_vien"
            + "(ma_nhan_vien, ten_nhan_vien, email, so_dien_thoai) "
            + "VALUES (:maNhanVien, :tenNhanVien, :email, :soDienThoai)";
    
//    public static final String QUERY_INSERT_NHAN_VIEN = "INSERT INTO nhan_vien"
//            + "(ma_nhan_vien, ten_nhan_vien, email, so_dien_thoai) "
//            + "VALUES (?, ?, ?, ?)";

    public static final String FILE_DATA = "data.csv";
}
