package enable;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
	@Import(MyHelloConfig.class)
	public static class AppConfig {
	}
	
	@Configuration
	public static class MyHelloConfig extends HelloConfig {

		@Override
		public void configName(NameRegistry registry) {
			registry.setName("Toby");
		}
	}
	
	public static class HelloConfig {
		@Bean Hello hello() {
			Hello hello = new Hello();
			configName(new HelloNameRegistry(hello));
			return hello;
		}
		
		public void configName(NameRegistry nameRegistry) {
		}
	}
	
	interface NameRegistry {
		void setName(String name);
	}
	
	static class HelloNameRegistry implements NameRegistry {
		Hello hello;
		
		public HelloNameRegistry(Hello hello) {
			this.hello = hello;
		}

		@Override
		public void setName(String name) {
			hello.setName(name);
		}
	}
}
