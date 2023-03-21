package com.example.passwordmanager.mail;

import com.example.passwordmanager.entity.Mail;
import com.example.passwordmanager.services.MailServiceImplementation;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor
class EmailHelpServiceTest {
    private final MailServiceImplementation mailServiceImplementation;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration
                    .aConfig().withUser("", ""));

    @Test
    void testSendEmail() throws Exception {

        Mail mail = new Mail();
        mail.setMailFrom("documentationfortesting@gmail.com");
        mail.setMailTo("testserviceimplonetimepasswprd@qa.team");
        mail.setMailSubject("Thanks!");
        mail.setMailContent("Thank you for reading my blogs!");
        mailServiceImplementation.sendEmail(mail);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage receivedMessage = receivedMessages[0];

        assertEquals("testemailservice@qa.team", receivedMessage.getFrom()[0].toString());
        assertEquals("Test email service", receivedMessage.getSubject().trim());
        assertEquals("Test email", receivedMessage.getContent().toString().trim());
    }
}
