import java.net.*;
import java.io.*;

public class MainClass {

    private static File research;

    public static void main(String[] args){
        int port= Integer.parseInt(args[0]);
        try{
            //creo una socket passiva su cui il server sta in ascolto
            ServerSocket s_socket= new ServerSocket(port);
            while(true){
                //accetto una richiesta di connessione
                Socket active_socket= s_socket.accept();
                BufferedReader reader= new BufferedReader(new InputStreamReader(active_socket.getInputStream()));
                //leggo la richiesta del client inviata dal browser e controllo che sia una richiesta che posso soddisfare
                boolean error=readRequest(reader);

                DataOutputStream response=new DataOutputStream(active_socket.getOutputStream());
                response.writeBytes("HTTP/1.0 200 OK\r\n");
                if(!error)
                    response.writeBytes("Usage: non-valid request\r\n");

                //invio la risposta alla richiesta che è stata fatta
                sendResponse(response);
                active_socket.close();
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private static boolean readRequest(BufferedReader reader) throws IOException{
        String sent=reader.readLine();
        String source="";
        //Controllo che la richiesta sia una GET altrimenti blocco il metodo e ritorno false
        if(sent.startsWith("GET")){
            source=sent;
        } else
            return false;

        //suddivido la stringa in base agli spazi
        String[] subseq=source.split("\\s+");
        String filename=subseq[1].substring(1);

        //Controllo che il nome del file abbia o spazi o meno,
        //se ha %20 nel nome, ha spazi e quindi sostituisco ogni occorrenza di %20 con " "
        if(filename.contains("%20"))
            filename=filename.replace("%20", " ");
        research=new File(filename);
        return true;
    }

    private static void sendResponse(DataOutputStream response) throws IOException{
        InputStream is=null;
        //se il file esiste costruisco la risposta, altrimenti restituisco un messaggio d'errore
        if(research.exists()) {
            is = new FileInputStream(research);
            byte[] data=new byte[(int) research.length()];
            is.read(data);

            response.writeBytes("Content-Length: " + data.length);
            response.writeBytes("\r\n\r\n");
            response.write(data);
        }else {
            response.writeBytes("\r\n\r\n");
            response.writeBytes("Usage: file doesn't exist\r\n\r\n");
        }
        if(is!=null)
            is.close();
    }
}
