package com.nevdev.witcher.services;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.*;

public class EmailServiceTest {

    private EmailService emailServiceUnderTest;

    @Before
    public void setUp() {
        emailServiceUnderTest = new EmailService();
        emailServiceUnderTest.emailSender = mock(MailSender.class);
    }

    @Test
    public void testSendEmail() {
        // Setup
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@witcher.com");
        message.setTo("to");
        message.setSubject("subject");
        message.setText("text");

        // Run the test
        emailServiceUnderTest.sendEmail("to", "subject", "text");

        // Verify the results
        verify(emailServiceUnderTest.emailSender).send(message);
    }
}
