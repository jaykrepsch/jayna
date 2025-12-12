package me.jayna.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

    private final Logger log = LoggerFactory.getLogger(MailConfiguration.class);

    private String host;

    private String port;

    private String username;

    private String password;

    public MailConfiguration(@Value("${spring.mail.host}") String host, @Value("${spring.mail.port}") String port,
            @Value("${spring.mail.username}") String username, @Value("${spring.mail.password}") String password) {

        log.debug("Initializing MailConfiguration with parameters host: {}, port: {}, username: {}", host, port,
                username);

        this.host = host;
        this.port = port;
        this.username = username;
//        this.password = new String(Base64.getDecoder().decode(password));
        this.password = password;
    }

    @Bean
    public JavaMailSender getMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(this.host);
        mailSender.setPort(Integer.valueOf(this.port));
        mailSender.setUsername(this.username);
        mailSender.setPassword(this.password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
