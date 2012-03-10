package pl.squirrel.money.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

	@RequestMapping("/hello.html")
	public String mav(ModelMap model) {
		String name = (String) model.get("name");
		name = "buzz";
		model.put("name", name);
		return "hello_velocity";
	}
}
