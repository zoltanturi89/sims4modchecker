package hu.diablo.sims4.mod.checker.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "hu.diablo.sims4.mod.checker.entities.repositories")
@EntityScan(basePackages = "hu.diablo.sims4.mod.checker.entities")
public class DatabaseConfiguration {

}
