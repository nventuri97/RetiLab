import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Task implements Runnable {
    private JSONObject account;
    private SharedVariables sh;

    public Task(JSONObject cc, SharedVariables sh){
        this.account=cc;
        this.sh=sh;
    }

    public void run() {
        JSONArray movements=(JSONArray) account.get("Movements");
        while(!movements.isEmpty()){
            JSONObject str=(JSONObject) movements.remove(0);
            String casual=(String) str.get("Casual");
            switch (casual) {
                case "Bonifico":
                    sh.addCasual(0);
                    break;
                case "Accredito":
                    sh.addCasual(1);
                    break;
                case "Bollettino":
                    sh.addCasual(2);
                    break;
                case "F24":
                    sh.addCasual(3);
                    break;
                case "PagoBancomat":
                    sh.addCasual(4);
                    break;
            }
        }
    }
}