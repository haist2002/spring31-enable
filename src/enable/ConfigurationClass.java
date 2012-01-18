package enable;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

import enable.bean.Hello;
import enable.support.BeanDefinitionUtils;

public class ConfigurationClass {
	public static void main(String[] args) {
//		GenericXmlApplicationContext ac = new GenericXmlApplicationContext(ConfigurationClass.class, "context.xml");
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		
		BeanDefinitionUtils.printBeanDefinitions(ac);
		
		Hello hello = ac.getBean(Hello.class);
		hello.sayHello();
 	}
	
	@Configuration
	@EnableHello
	public static class AppConfig {
	}

	@Import(HelloConfig.class)
	@interface EnableHello {
	}
	
	@Configuration
	public static class HelloConfig {
		@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
		@Bean Hello hello() {
			Hello hello = new Hello();
			hello.setName("Toby");
			return hello;
		}
		
	}
}
