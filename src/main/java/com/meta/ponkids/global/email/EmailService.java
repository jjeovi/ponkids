package com.meta.ponkids.global.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;


    // Thymeleaf 템플릿을 사용
    // to               : 받는 사람 이메일 주소
    // subject          : 이메일 제목
    // templateName     : 템플릿 파일명
    // variables        : 템플릿에 전달할 데이터
    @Async      // 비동기 처리로직 실행
    public void sendTemplateEmail( String to, String subject, String templateName, Map< String, Object > variables ) {

        Context context = new Context();
        context.setVariables( variables );

        String htmlContent = templateEngine.process( "email/" + templateName, context );

        sendEmail( to, subject, htmlContent );
    }


    private void sendEmail( String to, String subject, String text ) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper( message, true, "UTF-8" );
            helper.setTo( to );
            helper.setSubject( subject );
            helper.setText( text, true ); // true는 HTML을 의미
            emailSender.send( message );
        } catch ( MessagingException e ) {
            e.printStackTrace();
        }
    }

    private void saveAuthNumberInSession( String authNumber ) {
        // 현재 요청의 HttpSession을 가져옴
        ServletRequestAttributes attr = ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession( true ); // 세션이 없으면 새로 생성

        // 인증번호를 세션에 저장
        session.setAttribute( "authNumber", authNumber );
    }
}
