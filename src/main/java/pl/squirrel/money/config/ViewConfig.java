package pl.squirrel.money.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.tools.generic.ContextTool;
import org.apache.velocity.tools.generic.LinkTool;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import pl.squirrel.svt.VelocityTilesInitializer;

@Configuration
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
	public ViewResolver viewResolver() {
		VelocityViewResolver resolver = new VelocityViewResolver();
		resolver.setViewClass(VelocityToolboxView.class);
		// resolver.setToolboxConfigLocation("/WEB-INF/tools.xml");
		// new RequestFacade(request).getR
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("contextz", new ContextTool());
		attributes.put("linkz", new LinkTool());
		resolver.setAttributesMap(attributes);
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
}
