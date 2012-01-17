package enable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.ImportSelector;
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
	@EnableHello(type=1, name="Toby")
	public static class AppConfig {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Import(HelloConfigSelector.class)
	public @interface EnableHello {
		int type();
		String name();
	}
	
	static class HelloConfigSelector implements ImportSelector {
		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			int type = (Integer) importingClassMetadata.getAnnotationAttributes(
					EnableHello.class.getName()).get("type");
			
			return type == 1 ? new String[] { HelloConfig1.class.getName() } :
				new String[] { HelloConfig2.class.getName() };
		}
		
	}
	
	@Configuration
	public static class HelloConfig1 implements ImportAware {
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
	
	@Configuration
	public static class HelloConfig2 implements ImportAware {
		@Bean Hello hello() {
			Hello hello = new Hello();
			return hello;
		}
		
		@Autowired Hello hello;
		
		@Override
		public void setImportMetadata(AnnotationMetadata importMetadata) {
			hello.setName((String) 
					importMetadata.getAnnotationAttributes(
							EnableHello.class.getName()).get("name")+"2");
		}
	}
}
