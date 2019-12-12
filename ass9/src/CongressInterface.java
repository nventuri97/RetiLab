import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CongressInterface extends Remote {

    //Restituisce True se la registrazione andata a buon fine, false altrimenti
    public boolean registration(String speakerName, int day, int numberSection) throws RemoteException;

    //Restituisce l'organizzazione di tutta la conferenza
    public ArrayList<ArrayList<ArrayList<String>>> getConferenceOrganization() throws RemoteException;

    //Restituisce l'organizzazione del giorno day della conferenza
    public ArrayList<ArrayList<String>> getConferenceOrganization(int day) throws RemoteException;

    //Restituisce le operazioni possibili
    public ArrayList<String> getOperations() throws RemoteException;

    //Restistuisce l'i-esima sessione di tutti giorni
    public ArrayList<ArrayList<String>> getSession(int i) throws RemoteException;
}
