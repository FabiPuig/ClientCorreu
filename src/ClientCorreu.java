import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by 20464654j on 08/03/17.
 */
public class ClientCorreu {

    private Properties mailProperties;
    private Session mailSession;
    private MimeMessage genMailMessage;
    private Store store;

    private String mailEmisor = "";
    private String pass = "";
    private String mailReceptor = "";


    public ClientCorreu() {
        initialize();
    }

    /** Inicializa el gestor de correo hotmail
     *
     */
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

        // para leer correos. debes asegurarte que la cuenta de correo permita que otros dispositivos y/o aplicaciones
        // utilicen POP. esto esta en el correo Opciones/Correo/Cuentas/POP e IMAP
        mailProperties.setProperty("mail.pop3s.port",  "995");

        mailSession = Session.getDefaultInstance( mailProperties, null);

    }

    public void setMessage( String subject, String body, String path ) throws MessagingException {


        genMailMessage = new MimeMessage( mailSession );

        genMailMessage.addRecipient( Message.RecipientType.TO, new InternetAddress( mailReceptor ) );
        genMailMessage.setSubject( subject );


        // si hay path que incluya el texto y el fichero en el mensaje. en caso contrario que simplemente incluya el texto
        if( path != null){

            System.out.println("tiene path");
            // creamos parte de mensaje
            BodyPart messageBodyPart = new MimeBodyPart();

            // añadimos el texto/cuerpo del mensaje
            messageBodyPart.setText( body );

            // crea mensaje multipart
            Multipart multipart = new MimeMultipart();

            // añadimos el texto al multipart
            multipart.addBodyPart(messageBodyPart);

            // añadimos el fichero
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource( path );

            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName( source.getName() );
            multipart.addBodyPart(messageBodyPart);

            // añadimos el multipart al contenido
            genMailMessage.setContent(multipart);
        }else{
            genMailMessage.setContent( body, "text/html");
        }

        System.out.println("Mail Session has been created successfully..");

        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.live.com", mailEmisor, pass );
        transport.sendMessage( genMailMessage, genMailMessage.getAllRecipients() );
        transport.close();

    }
    public void getMessages(){

        try {
            // crea un objeto store pop3 y se conecta con el servidor pop
            store = mailSession.getStore("pop3s");

            store.connect("pop3.live.com", 995, mailEmisor, pass);

            // objeto folder i lo abrimos
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // extraemos los mensages y los ponemos en un array. luego los imprimimos
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            // los 10 ultimos mensajes
            for (int i = messages.length - 10; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + textMsg( message ) );

            }

            //cerramos el store y el folder
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Extrae el texto del mensaje
     *
     * @param msg
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    private static String textMsg(Message msg) throws MessagingException, IOException {
        String contentType = msg.getContentType();
        String messageContent="";

        if ( contentType.contains("multipart") ) {

            Multipart multiPart = (Multipart) msg.getContent();

            int numberOfParts = multiPart.getCount();

            for (int partCount = 0; partCount < numberOfParts; partCount++) {

                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                messageContent = part.getContent().toString();
            }
        }
        else if (contentType.contains("text/plain") || contentType.contains("text/html")) {

            Object content = msg.getContent();
            if (content != null) {
                messageContent = content.toString();
            }
        }
        return messageContent;
    }
}
