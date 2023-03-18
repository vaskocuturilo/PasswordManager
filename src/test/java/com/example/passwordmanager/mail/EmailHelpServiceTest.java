package com.example.passwordmanager.mail;

import com.example.passwordmanager.services.EmailHelpService;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmailHelpServiceTest {
    private final EmailHelpService emailHelpService;

    public EmailHelpServiceTest(EmailHelpService emailHelpService) {
        this.emailHelpService = emailHelpService;
    }

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration
                    .aConfig().withUser("", ""));

    @Test
    void testSendEmail() throws Exception {

        emailHelpService.sendEmail("testemailservice@qa.team", "Test email service", "Test email");

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage receivedMessage = receivedMessages[0];

        assertEquals("testemailservice@qa.team", receivedMessage.getFrom()[0].toString());
        assertEquals("Test email service", receivedMessage.getSubject().trim());
        assertEquals("Test email", receivedMessage.getContent().toString().trim());
    }
}
