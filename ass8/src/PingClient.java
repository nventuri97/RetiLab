import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Calendar;

public class PingClient {
    static int trasmitted=0;
    static int success=0;
    static int[] rtt=new int[10];

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
            int i=0;
            while(i<10) {
                //costruisco la stringa per inviare il messaggio
                long start_time=Calendar.getInstance().getTimeInMillis();
                String msg="PING "+(i+1)+" "+start_time;
                byte[] data=msg.getBytes();
                //compongo il datagram packet da inviare sul socket
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                //invio il datagram packet
                sock.send(packet);
                trasmitted++;

                //devo aspettare la risposta del server
                byte[] response=new byte[1024];
                DatagramPacket resp_packet=new DatagramPacket(response, 1024);
                try{
                //setto il timeout del socket
                sock.setSoTimeout(2000);
                sock.receive(resp_packet);
                }catch (SocketTimeoutException soe){
                    System.out.println("*");
                    rtt[i]=0;
                    i++;
                }
                if(response!=null) {
                    success++;
                    String answer=new String(response, 0, response.length);
                    long endtime=Calendar.getInstance().getTimeInMillis();
                    rtt[i]=(int) (endtime-start_time);
                    System.out.println(msg+" "+ rtt[i]);
                }
                i++;
            }
        } catch (UnknownHostException une){
            System.out.println("ERR -arg 1");
            return;
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        printStat();
    }

    static void printStat(){
        int p_loss=100-(trasmitted*100)/success;
        System.out.println(trasmitted+" packets trasmitted, "+success+" packets received, "+p_loss+"% loss");
        int tot=0;
        int max=rtt[0], min=rtt[0];
        for(int i=0;i<10;i++) {
            tot += rtt[i];
            if(rtt[i]>max)
                max=rtt[i];
            else if(rtt[i]<min)
                min=rtt[i];
        }
        float avg=tot/10;
        System.out.println( "round-trip (ms) min/avg/max = "+min+"/"+avg+"/"+max);
    }
}
