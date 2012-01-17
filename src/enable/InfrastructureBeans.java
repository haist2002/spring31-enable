package enable;

import org.springframework.context.support.GenericXmlApplicationContext;

import enable.support.BeanDefinitionUtils;

public class InfrastructureBeans {
	public static void main(String[] args) {
		GenericXmlApplicationContext ac = new GenericXmlApplicationContext(InfrastructureBeans.class, "infrastructure.xml");
		BeanDefinitionUtils.printBeanDefinitions(ac);
	}
}
