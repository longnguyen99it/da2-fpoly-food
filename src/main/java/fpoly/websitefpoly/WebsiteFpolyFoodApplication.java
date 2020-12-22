package fpoly.websitefpoly;

import fpoly.websitefpoly.config.AppProperties;
import fpoly.websitefpoly.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.Resource;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WebsiteFpolyFoodApplication {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(WebsiteFpolyFoodApplication.class, args);
    }
}
