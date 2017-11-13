package com.core.quartz;

import com.core.business.SpringContextHolder;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scheduler implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			this.registerTrigger();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.schedulerFactoryBean();

		SpringContextHolder.getBean(SchedulerFactoryBean.class).start();
	}
	
	public void registerTrigger() throws Exception{
		String[] names = SpringContextHolder.getApplicationContext().getBeanNamesForType(Quartz.class);
		
		for (String name : names) {
			Quartz quartz = SpringContextHolder.getBean(name, Quartz.class);
			String detailName = name+"Detail";
			
			BeanDefinitionBuilder detailBuilder = BeanDefinitionBuilder.genericBeanDefinition(JobDetailFactoryBean.class);
			detailBuilder.addPropertyValue("jobClass", quartz.getClass());
			detailBuilder.addPropertyValue("durability", true);
			detailBuilder.addPropertyValue("requestsRecovery", true);
			this.registerBean(detailName, detailBuilder.getRawBeanDefinition());
			
			BeanDefinitionBuilder triggerBuilder = BeanDefinitionBuilder.genericBeanDefinition(CronTriggerFactoryBean.class);
			triggerBuilder.addPropertyValue("jobDetail", SpringContextHolder.getBean(detailName));
			triggerBuilder.addPropertyValue("cronExpression", Quartz.class.getMethod("getCronExpression").invoke(quartz));
			this.registerBean(name+"Trigger", triggerBuilder.getRawBeanDefinition());
		}
	}
	
	public void schedulerFactoryBean(){
		// 通过BeanDefinitionBuilder创建bean定义  
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SchedulerFactoryBean.class);
		// 设置属性
		builder.addPropertyValue("dataSource", dataSource);
		builder.addPropertyValue("overwriteExistingJobs", true);
		builder.addPropertyValue("startupDelay", 10);
		builder.addPropertyValue("autoStartup", true);
		builder.addPropertyValue("applicationContextSchedulerContextKey", "applicationContextKey");
		builder.addPropertyValue("configLocation", new ClassPathResource(("spring-quartz.properties")));
		
		String[] names = SpringContextHolder.getApplicationContext().getBeanNamesForType(Trigger.class);
		List<Trigger> triggers = new ArrayList<>();
		for (String name : names) {
			triggers.add(SpringContextHolder.getBean(name, Trigger.class));
		}
		builder.addPropertyValue("triggers", triggers.toArray(new Trigger[0]));
		
		this.registerBean("schedulerFactoryBean", builder.getRawBeanDefinition());
	}
	
	private void registerBean(String beanName, AbstractBeanDefinition beanDefinition){
		//将applicationContext转换为ConfigurableApplicationContext  
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringContextHolder.getApplicationContext(); 
				
		// 获取bean工厂并转换为DefaultListableBeanFactory  
		BeanDefinitionRegistry defaultListableBeanFactory = (BeanDefinitionRegistry) configurableApplicationContext.getBeanFactory();
		// 注册bean  
		defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
		
	}
	
}
