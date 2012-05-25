package ca.sergiy.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolManager;
import org.apache.velocity.tools.view.context.ChainedContext;
import org.springframework.web.servlet.view.velocity.VelocityView;

public class VelocityToolsView extends VelocityView {

	// private static ToolContext toolContext = initVelocityToolContext();

	@Override
	protected Context createVelocityContext(Map model,
			HttpServletRequest request, HttpServletResponse response) {
		ToolContext toolContext = initVelocityToolContext();
		// VelocityContext context = new VelocityContext(toolContext);

		ChainedContext context = new ChainedContext(new VelocityContext(model,
				toolContext), getVelocityEngine(), request, response,
				getServletContext());

		// ToolboxManager toolboxManager = ServletToolboxManager.getInstance(
		// getServletContext(), getToolboxConfigLocation());
		// Map toolboxContext = toolboxManager.getToolbox(velocityContext);
		// velocityContext.setToolbox(toolboxContext);

		// if (model != null) {
		// for (Map.Entry<String, Object> entry : (Set<Map.Entry<String,
		// Object>>) model
		// .entrySet()) {
		// context.put(entry.getKey(), entry.getValue());
		// }
		// }
		return context;
	}

	private ToolContext initVelocityToolContext() {
		// if (toolContext == null) {
		System.out.println("KG " + getServletContext());
		ToolManager velocityToolManager = new ViewToolManager(
				getServletContext(), false, true);
		velocityToolManager.configure("tools.xml");
		return velocityToolManager.createContext();
		// toolContext = velocityToolManager.createContext();
		// }
		// return toolContext;
	}

	// @Override
	// protected Context createVelocityContext(
	// Map<String, Object> model, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	//
	// // Create a ChainedContext instance.
	// ChainedContext velocityContext = new ChainedContext(
	// new VelocityContext(model), getVelocityEngine(), request, response,
	// getServletContext());
	//
	// // Load a Velocity Tools toolbox, if necessary.
	// if (getToolboxConfigLocation() != null) {
	// ToolboxManager toolboxManager = ServletToolboxManager.getInstance(
	// getServletContext(), getToolboxConfigLocation());
	// Map toolboxContext = toolboxManager.getToolbox(velocityContext);
	// velocityContext.setToolbox(toolboxContext);
	// }
	//
	// return velocityContext;
	// }

	/**
	 * Overridden to check for the ViewContext interface which is part of the
	 * view package of Velocity Tools. This requires a special Velocity context,
	 * like ChainedContext as set up by {@link #createVelocityContext} in this
	 * class.
	 */
	@Override
	protected void initTool(Object tool, Context velocityContext)
			throws Exception {
		// Velocity Tools 1.3: a class-level "init(Object)" method.
		// Method initMethod = ClassUtils.getMethodIfAvailable(tool.getClass(),
		// "init", Object.class);
		// if (initMethod != null) {
		// ReflectionUtils.invokeMethod(initMethod, tool, velocityContext);
		// }
		super.initTool(tool, velocityContext);
	}
}