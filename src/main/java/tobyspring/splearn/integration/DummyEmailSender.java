package tobyspring.splearn.integration;

import tobyspring.splearn.application.required.EmailSender;
import tobyspring.splearn.domain.Email;

public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("Dummy email = " + email);
    }
}
