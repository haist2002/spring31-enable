package enable.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.GenericApplicationContext;

public class BeanDefinitionUtils {
	public static void printBeanDefinitions(GenericApplicationContext cwac) {
		List<List<String>> roleBeanInfos = new ArrayList<List<String>>();
		roleBeanInfos.add(new ArrayList<String>());
		roleBeanInfos.add(new ArrayList<String>());
		roleBeanInfos.add(new ArrayList<String>());
		
		for(String name : cwac.getBeanDefinitionNames()) {
			int role = cwac.getBeanDefinition(name).getRole();
			List<String> beanInfos = roleBeanInfos.get(role);
			beanInfos.add(role + " : " + name + " : " + cwac.getBean(name).getClass().getName());
		}
		
		for(List<String> beanInfos : roleBeanInfos) {
			for(String beanInfo : beanInfos) {
				System.out.println(beanInfo);
			}
		}
	}
}
