package borelgabriel.com.br.springwebstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "borelgabriel.com.br.springwebstore")
@EntityScan(basePackages = "borelgabriel.com.br.springwebstore.model")
@EnableJpaRepositories(basePackages = {"borelgabriel.com.br.springwebstore.repository"})
@EnableTransactionManagement
public class SpringWebstoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringWebstoreApplication.class, args);
    }
}
