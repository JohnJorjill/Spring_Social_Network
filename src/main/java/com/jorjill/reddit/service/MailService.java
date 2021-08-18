package com.jorjill.reddit.service;

import com.jorjill.reddit.exceptions.SpringRedditException;
import com.jorjill.reddit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    // send email
    @Async
    void sendMail(NotificationEmail notificationEmail){
      MimeMessagePreparator messagePreparator = mimeMessage -> {
          MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
          messageHelper.setFrom("springreddti@email.com");
          messageHelper.setTo(notificationEmail.getRecipient());
          messageHelper.setSubject(notificationEmail.getSubject());
          messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
      };
      try{
          mailSender.send(messagePreparator);
          log.info("Activation email send!!");
      }catch(MailException e){
          throw new SpringRedditException("Exception occured when sending mail to user ");
      }
    }

}
