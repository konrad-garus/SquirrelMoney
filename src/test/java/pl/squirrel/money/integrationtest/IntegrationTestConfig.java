package pl.squirrel.money.integrationtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import pl.squirrel.money.data.DaoTestConfig;
import pl.squirrel.money.data.SpendingDao;
import pl.squirrel.money.web.SpendingEditionController;

@Configuration
@EnableTransactionManagement
@Import(DaoTestConfig.class)
public class IntegrationTestConfig {
	@Autowired
	private SpendingDao spendingDao;

	@Bean
	public SpendingEditionController spendingEditionController() {
		return new SpendingEditionController(spendingDao);
	}
}
