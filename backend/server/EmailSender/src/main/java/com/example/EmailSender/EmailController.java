package com.example.EmailSender;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public EmailController(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}
	
	
	
	
	private int generateRandomNumber() {
        Random random = new Random();
        return 10000 + random.nextInt(90000); // Generate a number between 10000 and 99999
    }

	@PostMapping("/otp")
	public Map<String, Object> sendEmail(@RequestBody Map<String, String> requestBody) {
		String email = requestBody.get("email");
		Map<String, Object> response = new HashMap<>();
		try {
			int randomNumber = generateRandomNumber();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("nextlyfproperties@gmail.com");
			message.setTo(email);
			message.setSubject("OTP Verification From NextLyf");
//			message.setText("Your verification code is: " + randomNumber);
			message.setText("Dear User,\n"
					+ "\n"
					+ "Your OTP (One-Time Password) for verification is "+ randomNumber +". Please enter this code to complete the verification process.\n"
					+ "\n"
					+ "If you did not request this, please ignore this message.\n"
					+ "\n"
					+ "Thank you,\n"
					+ "NextLyf Properties");
			
			mailSender.send(message);
			response.put("email", email);
            response.put("otp", randomNumber);
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.put("error", "Error sending email to " + email);
            return response;
		}
		
		
	}
}
