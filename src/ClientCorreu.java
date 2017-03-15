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

        // añadir email del destinatario en InternetAddress
        genMailMessage.addRecipient( Message.RecipientType.TO, new InternetAddress("") );
        genMailMessage.setSubject( subject );
        genMailMessage.setContent( body, "text/html");
        System.out.println("Mail Session has been created successfully..");

        Transport transport = mailSession.getTransport("smtp");
        // añadir el correo hotmail y el pass de la cuenta de la que se enviara el correo
        transport.connect("smtp.live.com","","");
        transport.sendMessage( genMailMessage, genMailMessage.getAllRecipients() );
        transport.close();

    }
}
