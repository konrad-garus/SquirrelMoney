package pl.squirrel.money.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.config.ConfigurationUtils;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityView;

public class VelocityToolsView extends VelocityView {
	@Override
	protected Context createVelocityContext(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {
		ViewToolContext context = new ViewToolContext(getVelocityEngine(),
				request, response, getServletContext());

		ToolboxFactory factory = new ToolboxFactory();
		factory.configure(ConfigurationUtils.getVelocityView());

		for (String scope : Scope.values()) {
			context.addToolbox(factory.createToolbox(scope));
		}

		if (model != null) {
			for (Map.Entry<String, Object> entry : model.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}
		}
		return context;
	}
}
