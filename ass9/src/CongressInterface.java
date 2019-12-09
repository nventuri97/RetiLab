import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CongressInterface extends Remote {

    //Restituisce True se la registrazione andata a buon fine, false altrimenti
    public boolean registration(String speakerName, int day, int numberSection) throws RemoteException;

    //Restituisce l'organizzazione del giorno day della conferenza
    public String getConferanceOrganization(int day) throws RemoteException;

    //Restituisce l'organizzazione di tutta la conferenza
    public String getConferanceOrganization() throws RemoteException;

    //Restituisce le operazioni possibili
    public ArrayList<String> getOperations() throws RemoteException;

    //Restistuisce l'i-esima sessione di tutti giorni
    public String getSection(int i) throws RemoteException;
}
