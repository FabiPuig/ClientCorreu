import javax.mail.MessagingException;
import java.util.Scanner;

/**
 * Created by 20464654j on 08/03/17.
 */
public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Elige una opcion:");
        System.out.println("1-Escribir correo");
        //int i = in.nextInt();

        ClientCorreu cc = new ClientCorreu();
        try {
            cc.setMessage("hola","esto es una prova");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private void menuOption(int opcio){

        switch ( opcio ){
            case 1 :
                break;
            default:
                break;
        }
    }
}
