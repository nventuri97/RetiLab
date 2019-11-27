import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class PingServer {

    public static void main(String[] args){
        if(args.length>2){
            System.out.println("Too much argument");
            return;
        }

        int port;
        Random r=new Random();

        try{
            port = Integer.parseInt(args[0]);
        }catch (RuntimeException re){
            System.out.println("ERR -arg 1");
            return;
        }

        try{
            r.setSeed(Long.parseLong(args[1]));
        }catch (RuntimeException re){
            r.setSeed(0);
        }

        try {
            //creo la DatagramSocket su cui mi metto in ascolto
            DatagramSocket serversock = new DatagramSocket(port);
            while(true) {
                //costruisco il buffer e il datagram per ricevere il messaggio dal client
                byte[] data = new byte[1024];
                DatagramPacket server_pack = new DatagramPacket(data, 1024);
                //ricevo il datagram dal client
                serversock.receive(server_pack);
                //trasformo il messaggio in stringa e lo stampo

                //genero un tempo casuale in cui faccio sleep e poi invio la risposta
                long time=(long) (Math.random()*100);
                Thread.sleep(time);

                InetAddress client = server_pack.getAddress();
                int clport=server_pack.getPort();
                int casual=(int)(Math.random()*3)+1;
                if(casual==1) {
                    System.out.println(server_pack.getAddress() + " " + server_pack.getPort() + " " + new String(data, 0, data.length));
                    System.out.println("Ping non inviato");
                }else {
                    DatagramPacket response = new DatagramPacket(data, 1024, client, clport);
                    response.setData(data);
                    serversock.send(response);
                    System.out.println(server_pack.getAddress() + " " + server_pack.getPort() + " " + new String(data, 0, data.length));
                    System.out.println("Ping ritardato di " + time + " ms");
                }
            }
        } catch(SocketException soe){
            soe.printStackTrace();
        } catch(IOException ioe){
            ioe.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
