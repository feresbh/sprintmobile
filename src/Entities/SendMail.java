/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;



import com.sun.mail.smtp.SMTPTransport;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author Admin
 */
public class SendMail {

    public SendMail() {
    }
      
    
    public static void send(String to, String sub,String msg, final String user, final String pass,int Code) 
    {
        
           Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");	
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getDefaultInstance(props,new Authenticator() 
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication(user, pass);
            }
        });

        try 
        {
         Message message = new MimeMessage(session);

         message.setFrom(new InternetAddress(to));

         message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));

         message.setSubject("Confirmation reservation");

         MimeMultipart multipart = new MimeMultipart("related");

    
         BodyPart messageBodyPart = new MimeBodyPart();
         String htmlText = "<h1 style='color: Black'>Bonjour.\n" +
        "Votre reservation est effectuee avec succes!  </h1><h4></h4>";
         messageBodyPart.setContent(htmlText, "text/html");
         multipart.addBodyPart(messageBodyPart);

         message.setContent(multipart);
         // Send message
         Transport.send(message);

         System.out.println("Sent message successfully....");
           
            
         
            
        } catch (MessagingException e) 
        {
            System.out.println(e);
        }
   

        
    }
}
