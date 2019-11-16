import java.nio.*;
import java.net.*;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class EchoServer {
    public static int DefaultPort=6798;
    public static int MaxCapacity=1024;

    public static void main(String[] args){
        int port;
        try{
            port=Integer.parseInt(args[0]);
        } catch(RuntimeException ex){
            port=DefaultPort;
        }

        System.out.println("Listening for connections on port " + port);

        //Creo la server socket su cui il server si mette in ascolto
        ServerSocketChannel s_channel;
        Selector selector;

        try{
            //Creo la socket in ascolto
            s_channel=ServerSocketChannel.open();
            ServerSocket serverSocket=s_channel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            //Setting a non-blocking
            s_channel.configureBlocking(false);
            selector=Selector.open();
            s_channel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException ioe){
            ioe.printStackTrace();
            return;
        }

        
    }
}
