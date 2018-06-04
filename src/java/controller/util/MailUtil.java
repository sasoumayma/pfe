/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Adil
 */
public class MailUtil {

//    private static final String USER_NAME = null;  // GMail user name (just the part before "@gmail.com")
//    private static final String PASSWORD = null; // GMail password
//    private static final String compte = compteConfigFacade.lastCompte().getCompte();
//    private static final String passWord = compteConfigFacade.lastCompte().getPassWord();
//
//    private static final String USER_NAME CompteConfigFacade= compte;  // GMail user name (just the part before "@gmail.com")
//    private static final String PASSWORD = passWord; // GMail password

    public static String addColor(String msg, Color color) {
        String hexColor = String.format("#%06X", (0xFFFFFF & color.getRGB()));
        String colorMsg = "<FONT COLOR=\"#" + hexColor + "\">" + msg + "</FONT>";
        return colorMsg;
    }

    public static void sendFromGMail(String USER_NAME, String PASSWORD,String[] to, String subject, String body) throws MessagingException, IOException {

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
        //    message.addRecipient(Message.RecipientType.CC, new InternetAddress("a.jabli@gmail.com"));
            message.setSubject(subject);
            //logo app header
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                    + "<tr><td align=\"center\">" + body + " </td>"
                    + "    </tr>"
                    + "</table>", "text/html");
            multipart.addBodyPart(messageBodyPart);
            // Récupérer l'image de logo
//            messageBodyPart = new MimeBodyPart();
//            String fileName = "logo.png";
//            File file = new File(fileName);
//            DataSource fds = new FileDataSource(file.getCanonicalPath());
//            messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setDataHandler(new DataHandler(fds));
//            messageBodyPart.setHeader("Content-ID", "<logo>");
//            multipart.addBodyPart(messageBodyPart);
            // Récupérer l'image de pied mail
       //     messageBodyPart = new MimeBodyPart();
           // String filepiedName = "imgPied.png";
          //  File filepied = new File(filepiedName);
           // DataSource fdsPied = new FileDataSource(filepied.getCanonicalPath());
//            messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setDataHandler(new DataHandler(fdsPied));
//            messageBodyPart.setHeader("Content-ID", "<imgPied>");
//            multipart.addBodyPart(messageBodyPart);

            //
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

}
