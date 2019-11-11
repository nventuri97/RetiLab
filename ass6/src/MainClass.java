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
                if(!error)
                    response.writeBytes("Usage: non-valid request\r\n");

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

    private static void sendResponse(DataOutputStream response) throws IOException{
        InputStream is=null;
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
