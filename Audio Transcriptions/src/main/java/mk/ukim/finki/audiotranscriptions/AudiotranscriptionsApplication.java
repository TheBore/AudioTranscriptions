package mk.ukim.finki.audiotranscriptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AudiotranscriptionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudiotranscriptionsApplication.class, args);
    }

    @Configuration
    public static class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/");
            registry.addResourceHandler("/audios/**")
                    .addResourceLocations("classpath:/audios/");
        }
    }

}
