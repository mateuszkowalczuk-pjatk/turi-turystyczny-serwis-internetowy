package com.turi.infrastructure.common;

import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
@AllArgsConstructor
public class EmailSender
{
    private final JavaMailSender sender;

    public void sendEmail(final String email, final String subject, final Integer code)
    {
        try
        {
            final var message = sender.createMimeMessage();

            final var helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply.turi@gmail.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(getEmailContent(code, subject), true);

            sender.send(message);
        }
        catch (final Exception ex)
        {
            throw new BadRequestParameterException("Email cannot be send.");
        }
    }

    private String getEmailContent(final Integer code, final String title)
    {
        try
        {
            final var resource = new ClassPathResource("email-content.html");
            final var content = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
            return content.replace("{{code}}", code.toString()).replace("{{title}}", title);
        }
        catch (IOException ex)
        {
            throw new BadRequestParameterException("Invalid email content.");

        }
    }
}
