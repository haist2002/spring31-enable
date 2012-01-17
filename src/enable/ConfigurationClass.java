package enable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;

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
	public static class AppConfig implements NameConfigurer {
		@Override
		public void configName(Hello hello) {
			hello.setName("Toby");
		}
	}

	@Import(HelloConfig.class)
	@interface EnableHello {
	}
	
	@Configuration
	public static class HelloConfig  {
		@Autowired NameConfigurer configurer;
		
		@Bean Hello hello() {
			Hello hello = new Hello();
			configurer.configName(hello);
			return hello;
		}
		
		@Autowired Hello hello;

	}
	
	interface NameConfigurer {
		void configName(Hello hello);
	}
}
