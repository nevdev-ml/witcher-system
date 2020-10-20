package com.nevdev.witcher.application;

public interface IEmailService {
    void sendEmail(String to, String subject, String text);
}
