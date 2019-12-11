import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String args[]){
        if(args.length==0){
            System.out.println("Usage: Client port requested");
            return;
        }

        int port=1111;

        try{
            port=Integer.parseInt(args[0]);
        }catch (RuntimeException rue){
            System.out.println("Err -arg 1");
            rue.printStackTrace();
        }

        CongressInterface s_obj;
        Remote r_obj;

        boolean run=true;

        try{
            Registry reg= LocateRegistry.getRegistry(port);
            r_obj=reg.lookup("Congress-Server");
            s_obj=(CongressInterface) r_obj;

            //Creo lo scanner per l'input da tastiera
            Scanner in=new Scanner(System.in);

            System.out.println("Benventuti nel sistema del congresso\nOperazioni consentite: "+ s_obj.getOperations());
            while(run){
                System.out.print("Quale operaione effettuare? ");
                int op=Integer.parseInt(in.nextLine());

                switch (op){
                    case 1:
                        insertNewSpeaker(s_obj);
                        break;
                    case 2:
                        System.out.println("Agenda completa del congresso\n"+s_obj.getConferenceOrganization());
                        break;
                    case 3:
                        System.out.print("Inserire il giorno di cui si desidera visualizzare l'agenda: ");
                        int day=Integer.parseInt(in.nextLine());
                        System.out.println("Agenda del giorno "+day+"\n"+s_obj.getConferenceOrganization(day));
                        break;
                    case 4:
                        System.out.print("Inserire il numero di sessione d'interesse: ");
                        int section=Integer.parseInt(in.nextLine());
                        System.out.println("Agenda della sessione "+section+" per i giorni del congresso\n"+s_obj.getSection(section));
                        break;
                    case 5:
                        System.out.println("Sistema in fase di terminazione, arrivederci");
                        run=false;
                        break;
                    default:
                        System.out.println("Operazione insesistente");
                        break;
                }
            }
        } catch (Exception re){
            re.printStackTrace();
        }
    }

    public static void insertNewSpeaker(CongressInterface obj) throws RemoteException{
        Scanner in=new Scanner(System.in);
        System.out.print("Inseririre il nome dello speaker: ");
        String name=in.nextLine();
        System.out.print("Inserire il giorno in cui aggiungere lo speaker: ");
        int day=Integer.parseInt(in.nextLine());
        System.out.print("Inserire in quale sezione si vuole inserire lo speaker: ");
        int section=Integer.parseInt(in.nextLine());
        obj.registration(name, day, section);
    }
}
