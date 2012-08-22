package pl.squirrel.money.integrationtest;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import pl.squirrel.money.data.SpendingDao;
import pl.squirrel.money.web.SpendingEditionController;

@Configuration
@EnableTransactionManagement
public class IntegrationTestConfig {
	@Bean
	public DataSource dataSource() throws NamingException {
		Properties props = new Properties();
		try {
			props.load(IntegrationTestConfig.class
					.getResourceAsStream("/db.properties"));
			return new DriverManagerDataSource(props.getProperty("url"),
					props.getProperty("user"), props.getProperty("password"));
		} catch (Exception e) {
			throw new RuntimeException(
					"Test database is not configured. Copy src/test/resources/db.properties.sample to db.properties and update it appropriately.",
					e);
		}
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
			throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean
				.setPackagesToScan(new String[] { "pl.squirrel.money.entity" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		Properties props = new Properties();
		props.put("hibernate.dialect",
				"org.hibernate.dialect.PostgreSQLDialect");
		props.put("hibernate.hbm2ddl.auto", "create-drop");
		factoryBean.setJpaProperties(props);

		return factoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager()
			throws NamingException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean()
				.getObject());
		return transactionManager;
	}

	@Bean
	public SpendingDao spendingDao() {
		return new SpendingDao();
	}

	@Bean
	public SpendingEditionController spendingEditionController() {
		return new SpendingEditionController();
	}
}
