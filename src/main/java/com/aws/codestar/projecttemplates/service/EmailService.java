package com.aws.codestar.projecttemplates.service;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.aws.codestar.projecttemplates.suppot.MailTemplatesUtils;

@Component
public class EmailService {

	JavaMailSender mailSender;

	@PostConstruct
	public void init() {
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

		this.mailSender = mailSender;
	}
	
	public void sendValify4email(String userName, String userEmail, String url) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		String htmlMsg = new MailTemplatesUtils().bulid(userName, userEmail, url);
		try {
			helper = new MimeMessageHelper(mimeMessage,false,"utf-8");
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
		try {			
			mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
			helper.setTo(userEmail);
			helper.setSubject("BBM 帳號啟用信");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
}
