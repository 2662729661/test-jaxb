package com.aws.codestar.projecttemplates.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.aws.codestar.projecttemplates.controller.FileUploadController;
import com.aws.codestar.projecttemplates.controller.HelloWorldController;
import com.aws.codestar.projecttemplates.controller.LineLoginController;
import com.aws.codestar.projecttemplates.controller.SessionController;
import com.aws.codestar.projecttemplates.service.EmailService;

/**
 * Spring configuration for sample application.
 */
@Configuration
@ComponentScan({ "com.aws.codestar.projecttemplates.configuration" })
@PropertySource("classpath:application.properties")
@Import({MvcConfig.class,SecurityConfig.class,EmailService.class})
public class ApplicationConfig {

	@Bean
	public HelloWorldController helloWorld() {
		return new HelloWorldController();
	}

	@Bean
	public SessionController SessionController() {
		return new SessionController();
	}
	
	@Bean
	public FileUploadController fileUploadController() {
		return new FileUploadController();
	}
	
	@Bean
	public LineLoginController lineLoginController() {
		return new LineLoginController();
	}
	

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("zhaoyang.julian@gmail.com");
	    mailSender.setPassword("5L4u;654829097");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
}
