package mp.project.apiandroid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = { "controllers", "services", "repository", "models", "interfaces",
    "mp.project.apiandroid" })
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "models")
public class ApiAndroidApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiAndroidApplication.class, args);
  }

}
