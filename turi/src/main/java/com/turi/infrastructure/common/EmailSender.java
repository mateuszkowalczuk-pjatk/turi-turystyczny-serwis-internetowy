package com.turi.infrastructure.common;

import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class EmailSender
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    private final JavaMailSender sender;

    public void sendEmailCode(final String email, final String subject, final Integer code)
    {
        try
        {
            final var message = sender.createMimeMessage();

            final var helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply.turi@gmail.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(getEmailCodeContent(subject, code), true);

            sender.send(message);
        }
        catch (final Exception ex)
        {
            LOGGER.error("Error sending email: {}", ex.getMessage(), ex);
            throw new BadRequestParameterException("Email cannot be send.");
        }
    }

    private String getEmailCodeContent(final String title, final Integer code)
    {
        try (final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("email-code-content.html"))
        {
            if (inputStream == null)
            {
                throw new BadRequestParameterException("Resource email-code-content.html not found!");
            }

            final var content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return content.replace("{{title}}", title).replace("{{code}}", code.toString());
        }
        catch (final IOException ex)
        {
            LOGGER.error("Error reading email content: {}", ex.getMessage(), ex);
            throw new BadRequestParameterException("Invalid email content.");
        }
    }

    public void sendEmailReminder(final String email, final String subject, final String text, final String date)
    {
        try
        {
            final var message = sender.createMimeMessage();

            final var helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply.turi@gmail.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(getEmailRemindContent(subject, text, date), true);

            sender.send(message);
        }
        catch (final Exception ex)
        {
            LOGGER.error("Error sending email: {}", ex.getMessage(), ex);
            throw new BadRequestParameterException("Email cannot be send.");
        }
    }

    private String getEmailRemindContent(final String title, final String text, final String date)
    {
        try (final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("email-reminder-content.html"))
        {
            if (inputStream == null)
            {
                throw new BadRequestParameterException("Resource email-reminder-content.html not found!");
            }

            final var content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return content.replace("{{title}}", title).replace("{{text}}", text).replace("{{date}}", date);
        }
        catch (final IOException ex)
        {
            LOGGER.error("Error reading email content: {}", ex.getMessage(), ex);
            throw new BadRequestParameterException("Invalid email content.");
        }
    }
}
