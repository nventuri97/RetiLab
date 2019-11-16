import java.nio.*;
import java.net.*;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class EchoClient {
    public static int DefaultPort=6798;

    public static void main(String[] args){
        if(args.length==0){
            System.out.println("Usage: java EchoClient host [port] [sentence]");
            return;
        }

        //Controllo se il client ha inserito la porta, altrimenti assegno la porta di default
        int port;
        try{
            port=Integer.parseInt(args[0]);
        } catch(RuntimeException ex){
            port=DefaultPort;
        }

        try{
            //Apro la socket
            SocketChannel client=SocketChannel.open(new InetSocketAddress("localhost", port));
            String sentece=args[1];
            //Preparo il buffer con la stringa che devo inviare
            ByteBuffer buffer=ByteBuffer.wrap(sentece.getBytes());

            client.write(buffer);
            System.out.println("Message sent to the server");

            //Adesso devo passare in modalità lettura
            buffer.flip();
            String answer="";
            while(true){
                int len=client.read(buffer);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    answer += StandardCharsets.UTF_8.decode(buffer).toString();
                }
                buffer.clear();
                if(len==0)
                    break;
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            return;
        }
    }
}