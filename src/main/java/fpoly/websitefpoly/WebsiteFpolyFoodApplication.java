package fpoly.websitefpoly;

import fpoly.websitefpoly.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WebsiteFpolyFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteFpolyFoodApplication.class, args);
    }

}
