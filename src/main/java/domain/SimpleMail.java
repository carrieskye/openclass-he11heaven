package domain;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;


public class SimpleMail {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_AUTH_USER = "openclass.he11heaven@gmail.com";
    private static final String SMTP_AUTH_PWD  = "projectweek";
    

    /*public static void main(String[] args) throws Exception{
       new SimpleMail().test();
    }*/
    
    public SimpleMail() {
    }

    public void sendMail(Student student, OpenClassSession sessie) throws Exception{
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        // mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setContent("Beste " + student.getFirstName() + "\n\nU heeft zich ingeschreven voor de sessie: " + sessie.getTitle() + ". \nWe verwachten u op " + sessie.toStringDate() + " om " + sessie.toStringHour() + ". \nZorg dat je optijd bent! \n\nIndien u toch niet meer kan komen kan u altijd de inschrijving aanpassen of verwijderen op de site.\n\nMet vriendelijke groet \nHet UCLL-team", "text/plain");
        message.setFrom(new InternetAddress("me@myhost.org"));
        message.setSubject("Inschrijving Openlesdag UCLL");
        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress(student.getEmail()));

        transport.connect();
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = SMTP_AUTH_USER;
           String password = SMTP_AUTH_PWD;
           return new PasswordAuthentication(username, password);
        }
    }
}