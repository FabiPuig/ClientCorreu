import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by 20464654j on 08/03/17.
 */
public class ClientCorreu {

    private Properties mailProperties;
    private Session mailSession;
    private MimeMessage genMailMessage;

    private String mailEmisor = "";
    private String pass = "";
    private String mailReceptor = "";


    public ClientCorreu() {
        initialize();
    }

    private void initialize(){

        mailProperties = System.getProperties();
        // port de hotmail
        mailProperties.put("mail.smtp.port", "587");

        // para hotmail hay que especificar el host en las propiedades ( con gmail no hace falta esta linea )
        mailProperties.setProperty("mail.host", "smtp.live.com");

        // autenticacio
        mailProperties.put("mail.smtp.auth", "true");
        // seguretat
        mailProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

    }

    public void setMessage( String subject, String body ) throws MessagingException {

        mailSession = Session.getDefaultInstance( mailProperties, null);

        genMailMessage = new MimeMessage( mailSession );

        genMailMessage.addRecipient( Message.RecipientType.TO, new InternetAddress( mailReceptor ) );
        genMailMessage.setSubject( subject );
        genMailMessage.setContent( body, "text/html");
        System.out.println("Mail Session has been created successfully..");

        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.live.com", mailEmisor, pass );
        transport.sendMessage( genMailMessage, genMailMessage.getAllRecipients() );
        transport.close();

    }
}
