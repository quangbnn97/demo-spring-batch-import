package vn.tcx.demo.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import vn.tcx.demo.springbatch.domain.NhanVien;

public class NhanVienItemProcessor implements ItemProcessor<NhanVien, NhanVien>{

    @Override
    public NhanVien process(NhanVien nhanVien) throws Exception {
        return nhanVien;
    }
}
