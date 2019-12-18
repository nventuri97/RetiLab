import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Calendar;
import java.util.Date;

public class TimeServer {
    private static long interval=(long) (Math.random()*1000)+1;

    public static void main(String args[]){
        if(args.length!=2){
            System.out.println("Usage: ERR -arg multicast_port ipGroup");
            System.exit(1);
        }

        try{
            //costruisco l'InetAddress del server
            InetAddress ias=InetAddress.getByName(args[1]);
            //Creo la socket su cui si attaccherà il gruppo
            int port=Integer.parseInt(args[0]);
            DatagramSocket serversock=new DatagramSocket();
            serversock.setReuseAddress(true);
            System.out.println("TimeServer working");

            byte[ ] buff;
            Date data;

            while(true){
                data=new Date();
                buff=data.toString().getBytes();
                DatagramPacket packet=new DatagramPacket(buff, buff.length, ias, port);
                System.out.println(new String(packet.getData()));
                serversock.send(packet);
                Thread.sleep(interval);
            }
        } catch (IOException | InterruptedException ioe){
            ioe.printStackTrace();
        }
    }
}
