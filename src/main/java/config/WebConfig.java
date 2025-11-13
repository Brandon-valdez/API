package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Permite servir las imágenes subidas desde /home/azureuser/uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///home/dam/uploads/");
    }
}
