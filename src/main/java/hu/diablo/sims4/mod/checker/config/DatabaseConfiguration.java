package hu.diablo.sims4.mod.checker.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import hu.diablo.sims4.mod.checker.enitites.ModFile;
import hu.diablo.sims4.mod.checker.enitites.SimsModDetails;

@Configuration
@EnableJpaRepositories(basePackages = "hu.diablo.sims4.mod.checker.entities.repositories")
@EnableTransactionManagement
@EntityScan(basePackageClasses = {SimsModDetails.class, ModFile.class})
public class DatabaseConfiguration {
	
}
