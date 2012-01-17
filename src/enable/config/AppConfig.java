package enable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import enable.bean.Hello;

@Configuration
public class AppConfig {
	@Bean Hello hello() {
		Hello hello = new Hello();
		hello.setName("Toby");
		return hello;
	}
}
