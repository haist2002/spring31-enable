package enable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
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

	@Retention(RetentionPolicy.RUNTIME)
	@Import(HelloBeanDefinitionRegistry.class)
	public @interface EnableHello {
		String name();
	}
	
	public static class HelloBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar {
		@Override
		public void registerBeanDefinitions(
				AnnotationMetadata importingClassMetadata,
				BeanDefinitionRegistry registry) {
			BeanDefinition bd = new RootBeanDefinition(Hello.class);
			bd.getPropertyValues().add("name", 
					(String)importingClassMetadata.getAnnotationAttributes(EnableHello.class.getName()).get("name")
					);
			registry.registerBeanDefinition("hello", bd);
		}
		
	}
	
}
