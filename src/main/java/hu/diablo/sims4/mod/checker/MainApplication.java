package hu.diablo.sims4.mod.checker;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import hu.diablo.sims4.mod.checker.config.ApplicationConfiguration;
import hu.diablo.sims4.mod.checker.config.DatabaseConfiguration;
import hu.diablo.sims4.mod.checker.ui.MainWindow;


@SpringBootApplication
@EnableAutoConfiguration
public class MainApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MainApplication.class, 
				ApplicationConfiguration.class,DatabaseConfiguration.class)
				.headless(false).run(args);
	    MainWindow appFrame = context.getBean(MainWindow.class);
	    appFrame.setVisible(true);
	}
}
