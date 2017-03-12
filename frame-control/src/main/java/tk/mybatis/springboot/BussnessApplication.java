package tk.mybatis.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dhcc.ecm.business.util.BusinessProperties;


@EnableTransactionManagement
@SpringBootApplication
@EnableConfigurationProperties({BusinessProperties.class})
@ComponentScan(basePackages = "com.majesco.poc,tk.mybatis.springboot,com.dhcc.ecm.business.mybatis,com.dhcc.ecm.business")
public class BussnessApplication extends WebMvcConfigurerAdapter {


	
    public static void main(String[] args) {
        SpringApplication.run(BussnessApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("*")
    			.allowedMethods("PUT", "DELETE","GET","POST")
                .allowedHeaders("*")
    			.exposedHeaders("access-control-allow-headers",
    					"access-control-allow-methods",
    					"access-control-allow-origin",
    					"access-control-max-age",
    					"X-Frame-Options")
    			.allowCredentials(false).maxAge(3600);
            }
        };
    }
    
 
    @RequestMapping("/")
    String home() {
    	return "redirect:file/toUpload";
    }
}
