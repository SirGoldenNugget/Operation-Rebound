package org.minhvu.operationrebound;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server implements Runnable {
    private DatagramSocket serverSocket;
    private byte[] recieveData;
    private byte[] sendData;
    private Thread thread;

    private boolean running;

    private Box box = new Box();

    public Server() throws SocketException {
        serverSocket = new DatagramSocket(10000);
        recieveData = new byte[1024];
        sendData = new byte[1024];

        start();
    }

    public static void main(String[] args) throws SocketException {
        new Server();
    }

    public void run() {
        while (running) {
            try {
                DatagramPacket receivedPacket = new DatagramPacket(recieveData, recieveData.length);
                serverSocket.receive(receivedPacket);

                box = (Box) Network.deserialize(receivedPacket.getData());

                System.out.println(box.move);

                if (box.move == 1) {
                    box.x += 1;
                }

                InetAddress IPAddress = receivedPacket.getAddress();
                int port = receivedPacket.getPort();

                sendData = Network.serialize(box);

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(this);
        thread.start();
    }
}