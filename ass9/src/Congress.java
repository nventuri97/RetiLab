import java.rmi.*;
import java.rmi.server.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Congress extends RemoteServer implements CongressInterface{
    private static int days=3, section=12;
    private ArrayList<String> organizationD1, organizationD2, organizationD3;
    private ArrayList<String> operations;

    public Congress() throws RemoteException{
        this.organizationD1=this.organizationD2=this.organizationD3=new ArrayList<String>(section);

        for(int i=0;i<section;i++){
            organizationD1.add("Posto libero");
            organizationD2.add("Posto libero");
            organizationD3.add("Posto libero");
        }
    }

    @Override
    public boolean registration(String speakerName, int day, int numberSection) throws RemoteException {
        if(numberSection>12)
            throw new RemoteException("Numero sessione errato");

        switch (day){
            case(1):
                if(organizationD1.get(numberSection-1)!="Posto libero")
                    throw new RemoteException("Sessione già occupata");
                organizationD1.add(numberSection-1, speakerName);
                break;
            case(2):
                if(organizationD2.get(numberSection-1)!="Posto libero")
                    throw new RemoteException("Sessione già occupata");
                organizationD2.add(numberSection-1, speakerName);
                break;
            case(3):
                if(organizationD3.get(numberSection-1)!="Posto libero")
                    throw new RemoteException("Sessione già occupata");
                organizationD3.add(numberSection-1, speakerName);
                break;
            default:
                throw new RemoteException("Giorno della conferenza non valido");
        }
        return true;
    }

    @Override
    public ArrayList<ArrayList<String>> getConferenceOrganization() throws RemoteException {
        ArrayList<ArrayList<String>> fullConference=new ArrayList<>(3);

        fullConference.add(organizationD1);
        fullConference.add(organizationD2);
        fullConference.add(organizationD3);

        return fullConference;
    }

    @Override
    public ArrayList<String> getConferenceOrganization(int day) throws RemoteException {
        switch (day){
            case(1):
                return organizationD1;
            case(2):
                return organizationD2;
            case(3):
                return organizationD3;
            default:
                throw new RemoteException("Giorno della conferenza non valido");
        }
    }

    
}
