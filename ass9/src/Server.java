import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String args[]){
        if(args.length==0){
            System.out.println("Usage: Server port requested");
            System.exit(1);
        }
        //porta per collegarsi al server
        int port;

        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException ex) {
            System.out.println("ERR -arg 1");
            return;
        }

        try{
            //Creo l'oggetto remoto
            Congress congress=new Congress();

            //Esportazione dell'oggetto remoto
            CongressInterface stub = (CongressInterface) UnicastRemoteObject.exportObject(congress, 0);
            LocateRegistry.createRegistry(port);
            Registry r=LocateRegistry.getRegistry(port);

            r.rebind("Congress-Server", stub);
            System.out.println("Working Congress-Server");
        }catch (RemoteException re){
            re.printStackTrace();
        }
    }
}
