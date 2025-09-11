package cn.kryex.phonerepair;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("cn.kryex.phonerepair.mapper")
public class PhoneRepairApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneRepairApplication.class, args);
    }

}