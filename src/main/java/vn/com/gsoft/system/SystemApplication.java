package vn.com.gsoft.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(vn.com.gsoft.system.SystemApplication.class, args);
    }

}
