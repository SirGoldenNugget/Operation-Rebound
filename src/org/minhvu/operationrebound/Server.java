package org.minhvu.operationrebound;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server {
    private MulticastSocket serverSocket;
    private InetAddress address;
    private int port;

    private boolean running;

    private byte[] recieveData;
    private byte[] sendData;

    private Box box = new Box();

    public Server() throws IOException {
        serverSocket = new MulticastSocket();
        address = InetAddress.getByName("230.0.0.0");
        serverSocket.joinGroup(address);
        port = 10000;

        recieveData = new byte[1024];
        sendData = new byte[1024];

        running = true;

        new Thread(new Sender()).start();
        new Thread(new Reciever()).start();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

    private class Sender implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    if (box.x == 32) {
                        box.x = 64;
                    } else {
                        box.x = 32;
                    }

                    sendData = Network.serialize(box);

                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                    serverSocket.send(sendPacket);

                    Thread.yield();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            serverSocket.close();
        }
    }

    private class Reciever implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    DatagramPacket receivedPacket = new DatagramPacket(recieveData, recieveData.length);
                    serverSocket.receive(receivedPacket);

                    box = (Box) Network.deserialize(receivedPacket.getData());

                    System.out.println("Server " + box.move);

                    if (box.move) {
                        box.x += 1;
                    }

                    System.out.println("Server " + box.x);

                    Thread.yield();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            serverSocket.close();
        }
    }
}