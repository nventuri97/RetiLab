import java.net.*;
import java.io.*;
import java.io.IOException;

public class TimeClient {

    public static void main(String args[]){
        if(args.length!=2){
            System.out.println("Usage: ERR -arg multicast_port ipGroup");
            System.exit(1);
        }

        InetAddress group=null;
        int port=0;
        try{
            group=InetAddress.getByName(args[1]);
            port=Integer.parseInt(args[0]);

            //Creo la socket e mi aggiungo al multicast
            MulticastSocket clients=new MulticastSocket(port);
            clients.joinGroup(group);

            for(int i=0;i<10;i++){
                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                clients.receive(packet);
                if((new String(packet.getData()))==""){
                    System.out.println("Server crashed");
                    break;
                }
                System.out.println(new String(packet.getData()));
            }
            clients.leaveGroup(group);
            clients.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
