package LCK.snowTaxi2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "*")
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE")
                .exposedHeaders("Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers", "Authorization")
                .allowCredentials(true)
                .maxAge(300);
    }

}