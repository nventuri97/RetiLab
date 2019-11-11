import java.net.*;
import java.io.*;

public class MainClass {

    private static File research;

    public static void main(String[] args){
        int port=9999;
        try{
            ServerSocket s_socket= new ServerSocket(port);
            while(true){
                Socket active_socket= s_socket.accept();
                BufferedReader reader= new BufferedReader(new InputStreamReader(active_socket.getInputStream()));
                boolean error=readRequest(reader);

                DataOutputStream response=new DataOutputStream(active_socket.getOutputStream());
                response.writeBytes("HTTP/1.0 200 OK\r\n");
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private static boolean readRequest(BufferedReader reader) throws IOException{
        String sent=reader.readLine();
        String source="";
        boolean guard=true;
        if(sent.startsWith("GET")){
            source=sent;
        } else
            return false;

        String[] subseq=source.split("\\s+");
        String filename=subseq[1].substring(1);
        research=new File(filename);
        return true;
    }
}
