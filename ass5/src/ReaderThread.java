import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ReaderThread extends Thread {
    private SharedVariables sh;
    private String filePath;
    private JSONArray trans;

    public ReaderThread(String path, SharedVariables sh){
        this.filePath=path;
        this.sh=sh;
        this.trans=jsonReader(this.filePath);
    }

    public void run(){
        while(!trans.isEmpty()){
            JSONObject account=(JSONObject) trans.remove(0);
            sh.put(account);
        }
    }

    private JSONArray jsonReader(String filePath){
        JSONParser parser = new JSONParser();
        try {
            //apro il canale per la lettura
            FileChannel inputCh = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);
            //Creo il buffer per la lettura di 1 MB
            ByteBuffer buff = ByteBuffer.allocate(1024 * 1024);
            boolean stop = false;
            String str = "";
            while (!stop) {
                //leggo dal buffer e controllo di non essere arrivato a EOF
                int bytesRead = inputCh.read(buff);
                if (bytesRead == -1)
                    stop = true;
                //passo da modalità lettura in scrittura
                buff.flip();
                while (buff.hasRemaining())
                    str += StandardCharsets.UTF_8.decode(buff).toString();
                buff.clear();
            }
            //chiudo il canale di lettura
            inputCh.close();
            return (JSONArray) parser.parse(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
