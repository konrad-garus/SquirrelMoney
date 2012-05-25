package pl.squirrel.money.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.squirrel.money.tools.SpendingImporter;

@Configuration
@ComponentScan(basePackages = "pl.squirrel.money")
public class AppConfig {
	@Bean
	public Logger log() {
		Logger result = LoggerFactory.getLogger("SquirrelMoney");
		return result;
	}

	@Bean
	public SpendingImporter spendingImporter() {
		return new SpendingImporter();
	}
}