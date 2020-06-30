//package dell.example.com.letschat.UtilityClasses;
////
////import android.content.pm.PackageInstaller;
////import android.os.Message;
////
////
////import java.net.PasswordAuthentication;
////import java.security.Security;
////import java.util.Properties;
////
////public class MailSender extends javax.mail.Authenticator {
////    private String mailhost = "smtp.gmail.com"; //Hostname of the SMTP mail server which you want to connect for sending emails.
////    private String user;
////    private String password;
////    private PackageInstaller.Session session;
////
////    static {
////        Security.addProvider(new JSSEProvider());
////    }
////
////    public MailSender(String user, String password) {
////        this.user = user; //Your SMTP username. In case of GMail SMTP this is going to be your GMail email address.
////        this.password = password; //Your SMTP password. In case of GMail SMTP this is going to be your GMail password.
////
////        Properties props = new Properties();
////        props.setProperty("mail.transport.protocol", "smtp");
////        props.setProperty("mail.host", mailhost);
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.port", "465");
////        props.put("mail.smtp.socketFactory.port", "465");
////        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
////        props.put("mail.smtp.socketFactory.fallback", "false");
////        props.setProperty("mail.smtp.quitwait", "false");
////
////        session = PackageInstaller.Session.getDefaultInstance(props, this);
////
////    }
////
////    protected PasswordAuthentication getPasswordAuthentication() {
////        return new PasswordAuthentication(user, password);
////    }
////
////    public synchronized void sendMail(String subject, String body,
////                                      String sender, String recipients) throws Exception {
////        MimeMessage message = new MimeMessage(session);
////        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
////        message.setSender(new InternetAddress(sender));
////        message.setSubject(subject);
////        message.setDataHandler(handler);
////
////        if (recipients.indexOf(',') > 0)
////            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
////        else
////            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
////
////        Transport.send(message);
////    }
////}

//
//
//        new Thread(new Runnable() {
//
//@Override
//public void run() {
//        try {
//        MailSender sender = new MailSender("Your_Gmail_UserName@gmail.com",
//        "Your_Gmail_password");
//        sender.sendMail("This is a test subject", "This is the test body content",
//        "Your_Gmail_UserName@gmail.com", "recipient@example.com");
//        } catch (Exception e) {
//        Log.e("SendMail", e.getMessage(), e);
//        }
//        }
//
//        }).start();
