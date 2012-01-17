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
	@EnableHello(name="Spring")
	public static class AppConfig {
	}

	@Import(HelloConfig.class)
	@interface EnableHello {
		String name();
	}
	
	@Configuration
	public static class HelloConfig implements ImportAware {
		@Bean Hello hello() {
			Hello hello = new Hello();
			return hello;
		}
		
		@Autowired Hello hello;

		@Override
		public void setImportMetadata(AnnotationMetadata importMetadata) {
			hello.setName((String) 
					importMetadata.getAnnotationAttributes(
							EnableHello.class.getName()).get("name"));
		}
		
	}
}
