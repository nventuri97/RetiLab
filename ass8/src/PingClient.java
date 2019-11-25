import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class PingClient {
    static int trasmitted=0;
    static int success=0;

    public static void main(String[] args){
        if(args.length!=2){
            System.out.println("ERR Too much argument");
            return;
        }

        try{
            String server_name=args[0];
            int port=Integer.parseInt(args[1]);
            InetAddress address=InetAddress.getByName(server_name);
            DatagramSocket sock=new DatagramSocket();
            //setto il timeout del socket
            sock.setSoTimeout(2000);
            int i=0;
            while(i<10) {
                //costruisco la stringa per inviare il messaggio
                String msg="PING "+(i+1);
                byte[] data=msg.getBytes();
                //compongo il datagram packet da inviare sul socket
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                //invio il datagram packet
                sock.send(packet);
                trasmitted++;

                //devo aspettare la risposta del server
                byte[] response=new byte[30];
                DatagramPacket resp_packet=new DatagramPacket(response, 30);
                sock.receive(resp_packet);
                if(response==null)
                    System.out.println("*");
                else{
                    success++;
                    msg=response.toString();
                    System.out.println("msg");
                }
                i++;
            }
        } catch (UnknownHostException une){
            System.out.println("ERR -arg 1");
            return;
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
