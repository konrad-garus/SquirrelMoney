package pl.squirrel.money.web;

import java.io.IOException;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import pl.squirrel.svt.VelocityTilesInitializer;

@Configuration
@ComponentScan(basePackages = "pl.squirrel.money.web")
@EnableWebMvc
public class ViewConfig implements ApplicationContextAware {
	private ApplicationContext context;

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

	@Bean
	public VelocityConfig velocityConfig() {
		VelocityConfigurer cfg = new VelocityConfigurer();
		cfg.setResourceLoaderPath("/WEB-INF/velocity/");
		cfg.setConfigLocation(context
				.getResource("/WEB-INF/velocity.properties"));
		return cfg;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer cfg = new TilesConfigurer();
		cfg.setTilesInitializer(new VelocityTilesInitializer(velocityConfig()));
		return cfg;
	}

	@Bean
	public VelocityEngine velocityEngine() throws VelocityException,
			IOException {
		return new VelocityEngineFactory().createVelocityEngine();
	}

	@Bean
	public ViewResolver viewResolver() {
		VelocityViewResolver resolver = new VelocityViewResolver();
		resolver.setViewClass(VelocityToolsView.class);
		resolver.setSuffix(".vm");
		resolver.setPrefix("");
		resolver.setContentType("text/html; charset=utf-8");
		return resolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver result = new CommonsMultipartResolver();
		return result;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource result = new ResourceBundleMessageSource();
		result.setBasename("messages");
		return result;
	}
}
