package vn.tcx.demo.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import vn.tcx.demo.springbatch.constants.CommonConstants;
import vn.tcx.demo.springbatch.domain.NhanVien;
import vn.tcx.demo.springbatch.processor.NhanVienItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchImportConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

//    @Bean
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(CommonConstants.DATA_SOURCE_DRIVER_CLASS_NAME);
//        dataSource.setUrl(CommonConstants.DATA_SOURCE_URL);
//        dataSource.setUsername(CommonConstants.DATA_SOURCE_USER);
//        dataSource.setPassword(CommonConstants.DATA_SOURCE_PASSWORD);
//
//        return dataSource;
//    }

    @Bean
    public FlatFileItemReader<NhanVien> itemReader() {
        FlatFileItemReader<NhanVien> itemReader = new FlatFileItemReader<>();

        itemReader.setResource(new ClassPathResource(CommonConstants.FILE_DATA));
        itemReader.setLinesToSkip(1);

        DefaultLineMapper<NhanVien> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(
                new String[] { "maNhanVien", "tenNhanVien", "email", "soDienThoai" });

        BeanWrapperFieldSetMapper<NhanVien> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(NhanVien.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        itemReader.setLineMapper(lineMapper);

        return itemReader;
    }

    @Bean
    public NhanVienItemProcessor itemProcessor() {
        return new NhanVienItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<NhanVien> itemWriter() {
        JdbcBatchItemWriter<NhanVien> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(dataSource);
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.setSql(CommonConstants.QUERY_INSERT_NHAN_VIEN);

        return itemWriter;
    }
    

//    @Bean
//    public JdbcBatchItemWriter<NhanVien> itemWriter() {
//        JdbcBatchItemWriter<NhanVien> itemWriter = new JdbcBatchItemWriter<>();
//
//        itemWriter.setDataSource(dataSource);
//        itemWriter.setItemPreparedStatementSetter(new NhanVienPreparedStatementSetter());
//        itemWriter.setSql(CommonConstants.QUERY_INSERT_NHAN_VIEN);
//
//        return itemWriter;
//    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<NhanVien, NhanVien> chunk(500)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job nhanVienJob() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
}
