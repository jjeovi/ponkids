package com.meta.ponkids.global.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendAuthNumber(String to, String authNumber) throws MessagingException {
        // 템플릿에 전달할 데이터 설정
        Map<String, Object> variables = new HashMap<>();
        variables.put("authNumber", authNumber);

        // Thymeleaf 컨텍스트 생성
        Context context = new Context();
        context.setVariables(variables);

        // 템플릿 처리
        String htmlContent = templateEngine.process("email_template", context);

        // 이메일 설정 및 발송
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("인증번호 안내");
        helper.setText(htmlContent, true); // true를 사용하여 HTML로 보냄

        emailSender.send(message);
    }
}
