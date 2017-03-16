import javax.mail.MessagingException;
import java.io.File;
import java.util.Scanner;

/**
 * Created by 20464654j on 08/03/17.
 */
public class Main {

    public static ClientCorreu cc;
    private static Scanner instr;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        instr = new Scanner(System.in);
        int i;

        cc = new ClientCorreu();

        do{
            System.out.println("Elige una opcion:");
            System.out.println("1-Escribir correo");
            System.out.println("2-Leer correo (10 ultimos mensajes)");
            try{
                i = in.nextInt();
            }catch( Exception e){
                System.out.println("Debes escribir un numero");
                in.next();
                i = -1;
            }
        }while( !menuOption( i ) );

        System.out.println("byebye");

    }

    private static boolean menuOption( int opcio ){

        switch ( opcio ){
            case 1 :    sendMsg();
                break;
            case 2 :    cc.getMessages();
                break;
            case 0 : return true;
            default:
                break;
        }

        return false;
    }

    private static void sendMsg(){
        try {

            cc.setMessage( asunto(), mensaje(), fichero() );

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String asunto(){
        System.out.println("Escribe el asunto del mensaje:");
        String str = instr.nextLine();
        return str;
    }
    private static String mensaje(){
        System.out.println("Escribe el mensaje: ( todo en una linea )");
        String str = instr.nextLine();
        return str;
    }

    private static String fichero(){
        char c;
        do{
            System.out.println("Desea añadir un fichero? (s/n)");
            c = instr.next().charAt( 0 );
        }while( c != 's' && c != 'n' );
        String str;
        File f;
        if( c == 's' ){
            do{
                System.out.println("Indica el path del fichero:");
                str = instr.nextLine();
                f = new File(str);
            }while( !f.exists() );
            return str;
        }
        return null;
    }
}
