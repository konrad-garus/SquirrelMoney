package pl.squirrel.money.data;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="pl.squirrel.money.data")
public class PersistenceConfig {
	@Bean
	public DataSource dataSource() throws NamingException {
		return new JndiDataSourceLookup().getDataSource("jdbc/money");
		// TODO
		// JBoss
		// return new JndiTemplate().lookup("jdbc/money", DataSource.class);
		// return new JndiTemplate().lookup("java:/comp/env/jdbc/money",
		// DataSource.class); // Tomcat
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
			throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean
				.setPackagesToScan(new String[] { "pl.squirrel.money.entity" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		// vendorAdapter.setGenerateDdl(false); // TODO test only!
		// {
		// // JPA properties ...
		// }
		// };
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		Properties props = new Properties();
		props.put("hibernate.dialect",
				"org.hibernate.dialect.PostgreSQLDialect");
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
}
