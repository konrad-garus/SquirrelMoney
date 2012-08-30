package pl.squirrel.money.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pl.squirrel.money.data.PersistenceConfig;
import pl.squirrel.money.tools.SpendingImporter;
import pl.squirrel.money.web.ViewConfig;

@Configuration
@Import({ PersistenceConfig.class, ViewConfig.class })
// @ComponentScan(basePackages = "pl.squirrel.money")
public class AppConfig {

	public AppConfig() {
		System.out.println("Grr construct"); // TODO
	}

	@Bean
	public Logger log() {
		System.out.println("Grr"); // TODO
		Logger result = LoggerFactory.getLogger("SquirrelMoney");
		return result;
	}

	@Bean
	public SpendingImporter spendingImporter() {
		return new SpendingImporter();
	}
}
