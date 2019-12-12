import java.rmi.*;
import java.rmi.server.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Congress extends RemoteServer implements CongressInterface{
    private static int days=3, session=12;
    private ArrayList<ArrayList<String>> organizationD1, organizationD2, organizationD3;
    private ArrayList<String> operations;

    public Congress() throws RemoteException{
        this.organizationD1=new ArrayList<>(session);
        this.organizationD2=new ArrayList<>(session);
        this.organizationD3=new ArrayList<>(session);
        for(int i=0;i<session;i++){
            organizationD1.add(new ArrayList<String>());
            organizationD2.add(new ArrayList<String>());
            organizationD3.add(new ArrayList<String>());
        }

        this.operations=new ArrayList<>();
        operations.add("Aggiungi nuovo speaker");
        operations.add("Visulizza il programma completo del congresso");
        operations.add("Visulizza il programma di un giorno specifico");
        operations.add("Visulizza il programma di una specifica sessione per l'intero congresso");
        operations.add("Terminare il programma");
    }

    @Override
    public synchronized boolean registration(String speakerName, int day, int numberSession) throws RemoteException {
        if(numberSession>12)
            throw new RemoteException("Numero di sessione giornaliera inesistente");

        switch (day){
            case(1):
                if(organizationD1.get(numberSession-1).size()==5)
                    throw new RemoteException("Sessione completa, impossibile aggiungere nuovo speaker");
                organizationD1.get(numberSession-1).add(speakerName);
                return true;
            case(2):
                if(organizationD2.get(numberSession-1).size()==5)
                    throw new RemoteException("Sessione completa, impossibile aggiungere nuovo speaker");
                organizationD2.get(numberSession-1).add(speakerName);
                return true;
            case(3):
                if(organizationD3.get(numberSession-1).size()==5)
                    throw new RemoteException("Sessione completa, impossibile aggiungere nuovo speaker");
                organizationD3.get(numberSession-1).add(speakerName);
                return true;
            default:
                throw new RemoteException("Giorno congresso errato");
        }
    }

    @Override
    public ArrayList<ArrayList<ArrayList<String>>> getConferenceOrganization() throws RemoteException {
        ArrayList<ArrayList<ArrayList<String>>> fullOrganization =new ArrayList<>(3);

        fullOrganization.add(organizationD1);
        fullOrganization.add(organizationD2);
        fullOrganization.add(organizationD3);

        return fullOrganization;
    }

    @Override
    public ArrayList<ArrayList<String>> getConferenceOrganization(int day) throws RemoteException {
        switch (day){
            case(1):
                return organizationD1;
            case(2):
                return organizationD2;
            case(3):
                return organizationD3;
            default:
                throw new RemoteException("Giorno congresso errato");
        }
    }

    @Override
    public ArrayList<String> getOperations() {
        return operations;
    }

    @Override
    public ArrayList<ArrayList<String>> getSession(int i) throws RemoteException {
        if(i>session)
            throw new RemoteException("Numero di sessione giornaliera inesistente");
        ArrayList<ArrayList<String>> specificSession=new ArrayList<>();
        specificSession.add(organizationD1.get(i-1));
        specificSession.add(organizationD2.get(i-1));
        specificSession.add(organizationD3.get(i-1));

        return specificSession;
    }
}
