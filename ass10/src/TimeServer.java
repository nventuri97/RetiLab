import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Calendar;

public class TimeServer {
    private static long interval=(long) (Math.random()*100)+1;

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
            DatagramSocket serversock=new DatagramSocket(port);
            serversock.setReuseAddress(true);
            System.out.println("TimeServer working");

            while(true){
                byte[ ] data= Calendar.getInstance().toString().getBytes();
                DatagramPacket packet=new DatagramPacket(data, data.length, ias, port);
                serversock.send(packet);
                Thread.sleep(interval);
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
