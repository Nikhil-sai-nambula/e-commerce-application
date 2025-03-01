package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	private void sendHtmlEmail(String email, String htmlContent) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom("nikhil.nambula@gmail.com");
			helper.setTo(email);
			helper.setSubject("Email Verification");
			helper.setText(htmlContent, true);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendSimpleEmail(String email, String otp) {
//		String htmlContent = "<html>" + "<head><style>h1 {color: #4CAF50;}</style></head>" + "<body>"
//				+ "<h1>Welcome to Our ShopVerse!</h1>" + "<h3>One stop for a great Experience</h3>" + "<p>Your OTP is: <strong>" + otp + "</strong></p>"
//				+ "<p>Please use this OTP to verify your email address.</p>" + "</body>" + "</html>";

		String htmlContent = "<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				    "<meta charset='UTF-8'>" +
				    "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
				    "<title>Email Verification</title>" +
				    "<style>" +
				        "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }" +
				        ".container { max-width: 600px; margin: 0 auto; background: white; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
				        ".header { background: #4CAF50; padding: 30px; border-radius: 10px 10px 0 0; text-align: center; }" +
				        ".header h1 { color: white; margin: 0; font-size: 24px; }" +
				        ".content { padding: 40px 30px; text-align: center; }" +
				        ".otp-code { font-size: 32px; font-weight: bold; color: #4CAF50; margin: 25px 0; letter-spacing: 3px; }" +
				        ".instructions { color: #666666; line-height: 1.6; margin: 25px 0; }" +
				        ".footer { text-align: center; padding: 20px; background: #f8f9fa; border-radius: 0 0 10px 10px; color: #666666; font-size: 12px; }" +
				        ".logo { max-width: 150px; margin-bottom: 20px; }" +
				    "</style>" +
				"</head>" +
				"<body>" +
				    "<div class='container'>" +
				        "<div class='header'>" +
				            "<h1>Welcome to ShopVerse</h1>" +
				        "</div>" +
				        "<div class='content'>" +
				            "<h2 style='color: #333333; margin-bottom: 25px;'>Email Verification Required</h2>" +
				            "<p class='instructions'>" +
				                "To complete your email verification, please use the following One-Time Password (OTP):" +
				            "</p>" +
				            "<div class='otp-code'>" + otp + "</div>" +
				            "<p class='instructions'>" +
				                "This OTP will expire in 5 minutes.<br>" +
				                "If you didn't request this code, please ignore this email." +
				            "</p>" +
				        "</div>" +
				        "<div class='footer'>" +
				            "<p>Need help? Contact our support team at support@ShopVerse.com</p>" +
				            "<p>© 2025 ShopVerse. All rights reserved.</p>" +
				        "</div>" +
				    "</div>" +
				"</body>" +
				"</html>";
		
		sendHtmlEmail(email, htmlContent);
	}

}
