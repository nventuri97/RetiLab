import org.json.simple.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import static java.util.Calendar.*;

public class JSONGenerator {

    public static void main(String[] args){
        //Nomi e cognomi disponibili per gli utenti della generazione file JSON
        String[] name={"nome 1", "nome 2", "nome 3", "nome 4", "nome 5", "nome 6", "nome 7","nome 8"};
        String[] surname={"cognome 1", "cognome 2", "cognome 3", "cognome 4", "cognome 5", "cognome 6", "cognome 7", "cognome 8"};

        JSONArray accounts=new JSONArray();
        int n_ind, s_ind, cc, m_ind;
        //Inserisco un massimo di 10 clienti
        for(int i=0;i<10;i++){
            JSONObject account= new JSONObject();

            n_ind=(int) (Math.random()*7);
            account.put("Name", name[n_ind]);

            s_ind=(int) (Math.random()*7);
            account.put("Surname", surname[s_ind]);

            cc=(int) (Math.random()*1000000);
            account.put("CC", "IT"+cc);

            account.put("Movements", randomMovements());

            accounts.add(account);
        }

        try {
            File file=new File("Account.json");
            FileWriter fileW=new FileWriter(file);

            fileW.write(accounts.toJSONString());
            fileW.flush();
            fileW.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static int randBetween(int start, int end) {
        return start
                + (int) Math.round(Math.random() * (end - start));
    }

    private static String getRandomDate() {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(2017, 2019);

        gc.set(YEAR, year);

        int dayOfYear = randBetween(1,
                gc.getActualMaximum(DAY_OF_YEAR));

        gc.set(DAY_OF_YEAR, dayOfYear);

        return gc.get(YEAR) + "-" + (gc.get(MONTH) + 1) + "-"
                + gc.get(DAY_OF_MONTH);
    }

    private static JSONArray randomMovements(){
        //Movimenti effettuati in 2 anni
        int nTran=(int) (Math.random()*100);
        //Movimenti disponibili
        String[] movements={"Bonifico", "Accredito", "Bollettino", "F24", "PagoBancomat"};

        JSONArray movs= new JSONArray();
        for(int i=0;i<nTran;i++){
            JSONObject tran=new JSONObject();

            int m_ind=(int) (Math.random()*4);
            tran.put("date", getRandomDate());
            tran.put("Casual", movements[m_ind]);

            movs.add(tran);
        }
        return movs;
    }
}
