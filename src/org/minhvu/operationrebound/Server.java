package org.minhvu.operationrebound;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server implements Runnable {
    private MulticastSocket serverSocket;
    private InetAddress address;
    private int port;

    private byte[] recieveData;
    private byte[] sendData;
    private Thread thread;

    private boolean running;

    private Box box = new Box();

    public Server() throws IOException {
        serverSocket = new MulticastSocket();
        address = InetAddress.getByName("230.0.0.0");
        serverSocket.joinGroup(address);
        port = 10000;

        recieveData = new byte[1024];
        sendData = new byte[1024];

        start();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public void run() {
        while (running) {
            try {
//                DatagramPacket receivedPacket = new DatagramPacket(recieveData, recieveData.length);
//                serverSocket.receive(receivedPacket);
//
//                box = (Box) Network.deserialize(receivedPacket.getData());
//
//                System.out.println("Server " + box.move);
//
//                if (box.move) {
//                    box.x += 1;
//                }
//
//                System.out.println("Server " + box.x);

                if (box.x == 32) {
                    box.x = 64;
                } else {
                    box.x = 32;
                }

                sendData = Network.serialize(box);

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        stop();
    }

    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        serverSocket.close();

        System.exit(1);
    }
}