package me.home.configuration;

import me.home.banner.VitaminBannerPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * User: before30 
 * Date: 2016. 10. 13.
 * Time: 오후 4:36
 */
@Component
public class ApplicationEventListener {

	@Autowired
	private Environment env;

	@Autowired
	private ResourceLoader resourceLoader;

	@EventListener({ContextRefreshedEvent.class})
	void contextRefreshedEvent() {
		VitaminBannerPrinter vitaminBannerPrinter = new VitaminBannerPrinter(resourceLoader);
		vitaminBannerPrinter.print(env, System.out);
	}
}
