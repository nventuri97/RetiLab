import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PingServer {

    public static void main(String[] args){
        if(args.length>2){
            System.out.println("Too much argument");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
            //creo la DatagramSocket su cui mi metto in ascolto
            DatagramSocket serversock = new DatagramSocket(port);
            while(true) {
                //costruisco il buffer e il datagram per ricevere il messaggio dal client
                byte[] data = new byte[30];
                DatagramPacket server_pack = new DatagramPacket(data, 30);
                //ricevo il datagram dal client
                serversock.receive(server_pack);
                //trasformo il messaggio in stringa e lo stampo
                System.out.println(new String(data, 0, data.length));

                //genero un tempo casuale in cui faccio sleep e poi invio la risposta
                long time=(long) (Math.random()*100);
                Thread.sleep(time);

                InetAddress client = server_pack.getAddress();
                int clport=server_pack.getPort();
                DatagramPacket response=new DatagramPacket(data, 30, client, clport);
            }
        } catch(SocketException soe){
            System.out.println("ERR -arg 1");
            return;
        } catch(IOException ioe){
            ioe.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
