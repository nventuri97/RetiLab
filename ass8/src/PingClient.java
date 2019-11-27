import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Calendar;

public class PingClient {
    static int trasmitted=0;
    static int success=0;
    static int[] rtt=new int[10];

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("ERR Too much argument");
            return;
        }
        String server_name;
        int port;

        try{
            server_name = args[0];
        }catch (RuntimeException re){
            System.out.println("ERR -arg 1");
            return;
        }

        try{
            port = Integer.parseInt(args[1]);
        } catch (RuntimeException re){
            System.out.println("ERR -arg 2");
            return;
        }

        try {
            InetAddress address = InetAddress.getByName(server_name);
            DatagramSocket sock = new DatagramSocket();
            int i = 0;
            while (i < 10) {
                //costruisco la stringa per inviare il messaggio
                long start_time = Calendar.getInstance().getTimeInMillis();
                String msg = "PING " + (i + 1) + " " + start_time;
                byte[] data = msg.getBytes();
                //compongo il datagram packet da inviare sul socket
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                //invio il datagram packet
                packet.setData(data);
                sock.send(packet);
                trasmitted++;

                //devo aspettare la risposta del server
                byte[] response = new byte[1024];
                DatagramPacket resp_packet = new DatagramPacket(response, 1024);
                boolean lost = false;
                try {
                    //setto il timeout del socket
                    sock.setSoTimeout(2000);
                    sock.receive(resp_packet);
                } catch (SocketTimeoutException soe) {
                    //se ricevo la fine del timeout
                    System.out.println("*");
                    rtt[i] = 0;
                    //setto lost a true così da non verificare le condizioni successive
                    lost = true;
                } finally {
                    if (!lost) {
                        success++;
                        String answer = new String(response, 0, response.length);
                        //prendo l'endtime e mi calcolo l'rtt come differenza tra tempo di arrivo e tempo di partenza
                        long endtime = Calendar.getInstance().getTimeInMillis();
                        rtt[i] = (int) (endtime - start_time);
                        System.out.println(msg + " RTT:" + rtt[i]);
                    }
                    i++;
                }
            }
        } catch (UnknownHostException une){
            une.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        printStat();
    }

    static void printStat(){
        int p_loss=100-(success*100/trasmitted);
        System.out.println("--------STATISTICS--------");
        System.out.println(trasmitted+" packets trasmitted, "+success+" packets received, "+p_loss+"% loss");
        int tot=0;
        int max=1, min=100000;
        for(int i=0;i<10;i++) {
            tot += rtt[i];
            if(rtt[i]!=0)
                min=Math.min(min, rtt[i]);
            max=Math.max(max, rtt[i]);
        }
        float avg=(float)tot/success;
        System.out.printf("round-trip (ms) min/avg/max = %d/%.2f/%d\n", min, avg, max);
    }
}
