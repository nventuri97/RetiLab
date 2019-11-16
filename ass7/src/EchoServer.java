import java.nio.*;
import java.net.*;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

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

        while (true) {
            // Seleziona i canali pronti per almeno una delle operazioni di I/O tra quelli registrati con quel selettore
            try {
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys= selector.selectedKeys();
            Iterator<SelectionKey> iterator=readyKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key=iterator.next();
                //rimuove la chiave dal Selected set ma non dal registered set
                iterator.remove();
                try{
                    if(key.isAcceptable()){
                        ServerSocketChannel server=(ServerSocketChannel) key.channel();
                        //Creo una active socket derivata dalla accept sulla passive socket su cui il server è in ascolto
                        SocketChannel client=server.accept();
                        System.out.println("Accepted connection from "+ client);
                        //Setto a non-blocking
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if(key.isReadable()){
                        canBeRead(key);
                    } else if(key.isWritable())
                        canBeWrite(key);
                } catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }

    //Metodo per la lettura del messaggio inviato dal client
    public static void canBeRead(SelectionKey key) throws IOException{
        SocketChannel client= (SocketChannel) key.channel();
        ByteBuffer buff=ByteBuffer.allocate(MaxCapacity);
        String cl_msg="";
        int len=0;
        while(true){
            len=client.read(buff);
            if(len==0)
                break;
            buff.flip();
            while (buff.hasRemaining())
                cl_msg+= StandardCharsets.UTF_8.decode(buff).toString();
            buff.clear();
        }

        if(len==-1){
            System.out.println("Aborted connection by client");
            client.close();
            key.cancel();
        } else {
            System.out.println("Message received from client " + client);
            cl_msg+=" echod by server";
            SelectionKey key2=key.interestOps(SelectionKey.OP_WRITE);
            ByteBuffer response=ByteBuffer.wrap(cl_msg.getBytes());
            key2.attach(response);

        }
    }

    public static void canBeWrite(SelectionKey key) throws IOException{
        
    }
}
