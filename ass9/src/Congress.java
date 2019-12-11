import java.rmi.*;
import java.rmi.server.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Congress extends RemoteServer implements CongressInterface{
    private static int days=3, section=12;
    private ArrayList<ArrayList<String>> organizationD1, organizationD2, organizationD3;
    private ArrayList<String> operations;

    public Congress() throws RemoteException{
        this.organizationD1=new ArrayList<>(section);
        this.organizationD2=new ArrayList<>(section);
        this.organizationD3=new ArrayList<>(section);
        for(int i=0;i<section;i++){
            organizationD1.add(new ArrayList<String>(5));
            organizationD2.add(new ArrayList<String>(5));
            organizationD3.add(new ArrayList<String>(5));
        }

        this.operations=new ArrayList<>();
        operations.add("Aggiungi nuovo speaker");
        operations.add("Visulizza il programma completo del congresso");
        operations.add("Visulizza il programma di un giorno specifico");
        operations.add("Visulizza il programma di una specifica sessione per l'intero congresso");
        operations.add("Terminare il programma");
    }

    @Override
    public boolean registration(String speakerName, int day, int numberSection) throws RemoteException {
        if(numberSection>12)
            throw new RemoteException("Numero di sessione giornaliera inesistente");

        switch (day){
            case(1):
                if(organizationD1.get(numberSection-1).size()==5)
                    throw new RemoteException("Sessione completa, impossibile aggiungere nuovo speaker");
                organizationD1.get(numberSection-1).add(speakerName);
                return true;
            case(2):
                if(organizationD2.get(numberSection-1).size()==5)
                    throw new RemoteException("Sessione completa, impossibile aggiungere nuovo speaker");
                organizationD2.get(numberSection-1).add(speakerName);
                return true;
            case(3):
                if(organizationD3.get(numberSection-1).size()==5)
                    throw new RemoteException("Sessione completa, impossibile aggiungere nuovo speaker");
                organizationD3.get(numberSection-1).add(speakerName);
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
    public ArrayList<ArrayList<String>> getSection(int i) throws RemoteException {
        if(i>section)
            throw new RemoteException("Numero di sessione giornaliera inesistente");
        ArrayList<ArrayList<String>> specificSection=new ArrayList<>();
        specificSection.add(organizationD1.get(i-1));
        specificSection.add(organizationD2.get(i-1));
        specificSection.add(organizationD3.get(i-1));

        return specificSection;
    }
}
