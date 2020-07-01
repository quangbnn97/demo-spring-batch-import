DROP TABLE IF EXISTS springbatch;

CREATE TABLE nhan_vien  (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    ma_nhan_vien VARCHAR(255),
    ten_nhan_vien VARCHAR(255),
    email VARCHAR(255),
    so_dien_thoai BIGINT
);