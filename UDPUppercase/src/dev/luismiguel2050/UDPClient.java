package dev.luismiguel2050;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * client will send a message through packets and await for message returned in uppercase letters
 */
public class UDPClient {

    /**
     * host and port required by DatagramPacket when sending message
     * byte array required by DatagramPacket when sending and receiving.
     */
    private String host = "localhost";
    public static final int PORT = 4000;
    private byte[] buffer;

    /**
     * DatagramSocket allows receiving and sending packets
     */
    private DatagramSocket socket;


    /**
     * open socket so we can send and receive packets. There are no connections established between client and server.
     */
    public UDPClient(){
        try{
            socket = new DatagramSocket();

        } catch (SocketException exception){
            System.out.println(exception.getMessage());
        }
    }


    public void sendAndReceive(){
        System.out.println("Welcome to UDP messages to Uppercase.\nType the messages you would like to see in uppercase.");
        while(true){
            BufferedReader input = null;

            try{
                /**
                 * Prepare message input using BufferedReader
                 * prepare byte[]
                 * prepare packet, using byte[] as buffer, buffer length, the IP, using host provided and the port
                 * send the packet using socket;
                 */
                input = new BufferedReader(new InputStreamReader(System.in));

                String message ;
                while(!(message = input.readLine()).equals("")) {

                    buffer = message.getBytes();

                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host), PORT);

                    socket.send(packet);
                    /**
                     * if the input is /exit then the program stops running
                     */
                    if(message.equalsIgnoreCase("/exit")){
                        System.out.println("Come again if you want to see stuff in uppercase! Bye!");
                        System.exit(0);
                    }

                    /**
                     * prepare the packet to receive packet from server
                     * create String from packet data and print
                     */
                    packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(message);
                }
            } catch (IOException exception){
                System.out.println(exception.getMessage());
            } finally {
                if(input!=null){
                    try {
                        input.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }

    public static void main(String[] args) {

        UDPClient client = new UDPClient();
        client.sendAndReceive();



    }
}
