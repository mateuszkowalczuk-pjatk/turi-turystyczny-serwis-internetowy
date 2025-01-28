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

    public void sendEmailCode(final String email, final String subject, final Integer code)
    {
        try
        {
            final var message = sender.createMimeMessage();

            final var helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply@turi.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(getEmailCodeContent(subject, code), true);

            sender.send(message);
        }
        catch (final Exception ex)
        {
            throw new BadRequestParameterException("Email cannot be send.");
        }
    }

    private String getEmailCodeContent(final String title, final Integer code)
    {
        try
        {
            final var resource = new ClassPathResource("email-code-content.html");
            final var content = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
            return content.replace("{{title}}", title).replace("{{code}}", code.toString());
        }
        catch (final IOException ex)
        {
            throw new BadRequestParameterException("Invalid email content.");
        }
    }

    public void sendEmailReminder(final String email, final String subject, final String text, final String date)
    {
        try
        {
            final var message = sender.createMimeMessage();

            final var helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply@turi.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(getEmailRemindContent(subject, text, date), true);

            sender.send(message);
        }
        catch (final Exception ex)
        {
            throw new BadRequestParameterException("Email cannot be send.");
        }
    }

    private String getEmailRemindContent(final String title, final String text, final String date)
    {
        try
        {
            final var resource = new ClassPathResource("email-reminder-content.html");
            final var content = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
            return content.replace("{{title}}", title).replace("{{text}}", text).replace("{{date}}", date);
        }
        catch (final IOException ex)
        {
            throw new BadRequestParameterException("Invalid email content.");
        }
    }
}
