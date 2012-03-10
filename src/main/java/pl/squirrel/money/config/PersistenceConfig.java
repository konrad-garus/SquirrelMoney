package pl.squirrel.money.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {
	@Bean
	public DataSource dataSource() throws NamingException {
		return new JndiTemplate().lookup("jdbc/money", DataSource.class);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
			throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean
				.setPackagesToScan(new String[] { "pl.squirrel.money.entity" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		// {
		// // JPA properties ...
		// }
		// };
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		// factoryBean.setJpaProperties(additionlProperties());

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
}
