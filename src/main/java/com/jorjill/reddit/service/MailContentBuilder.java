package com.jorjill.reddit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    // get message and build email using thymeleaf template
    String build(String message){
        Context context = new Context();
        context.setVariable("message",message);
        return templateEngine.process("mailTemplate",context);
    }
}
