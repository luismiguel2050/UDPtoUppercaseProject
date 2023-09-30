package dev.luismiguel2050;

import java.io.IOException;
import java.net.*;

/**
 * Server will receive a String from client, through a packet, turn it to Uppercase and send it back
 */
public class UDPServer {

    /**
     * the buffer is required by the Datagrams packets
     */
    private byte[] buffer;
    /**
     * with Datagram sockets we can receive and send packets
     */
    private DatagramSocket socket;

    /**
     * open socket in port 4000
     * iniciate a byte[] buffer with the chosen capacity
     */
    public UDPServer(){
        try {
            socket = new DatagramSocket(4000);
            buffer = new byte[1024];
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveAndSend(){
        while(true){
            try{
                /**
                 *
                 */
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                System.out.println("waiting for messages");
                socket.receive(packet);

                InetAddress IP = packet.getAddress();
                int port = packet.getPort();

                String message = new String(packet.getData(), 0 , packet.getLength());
                System.out.println("Client said: " + message );
                if(message.equalsIgnoreCase("/exit")){
                    System.out.println("closing server");
                    break;
                }


                buffer = message.toUpperCase().getBytes();
                packet = new DatagramPacket(buffer, buffer.length, IP, port);
                socket.send(packet);
                buffer = new byte[1024];
            } catch (IOException exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        UDPServer server = new UDPServer();
        server.receiveAndSend();
    }

}
