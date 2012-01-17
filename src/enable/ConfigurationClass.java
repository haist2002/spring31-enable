package enable;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;

import enable.bean.Hello;
import enable.support.BeanDefinitionUtils;

public class ConfigurationClass {
	public static void main(String[] args) {
//		GenericXmlApplicationContext ac = new GenericXmlApplicationContext(ConfigurationClass.class, "context.xml");
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		
		BeanDefinitionUtils.printBeanDefinitions(ac);
		
		Hello hello = ac.getBean(Hello.class);
		hello.sayHello();
		
		AppConfig appConfig = ac.getBean(AppConfig.class);
		System.out.println(hello);
		System.out.println(appConfig.hello());
		System.out.println(appConfig.hello());
		System.out.println(appConfig.hello());
 	}
	
	@Configuration
	public static class AppConfig {
		@Bean Hello hello() {
			Hello hello = new Hello();
			hello.setName("Toby");
			return hello;
		}
	}
}
