package org.minhvu.operationrebound;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable {
    private int port;
    private boolean running = true;
    private Selector selector;
    private ServerSocket serverSocket;
    private ByteBuffer byteBuffer;

    public Server(int port, int bufferSize) {
        this.port = port;
        this.byteBuffer = ByteBuffer.allocate(bufferSize);
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            try {
                int client = selector.select();

                if (client == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                        Socket socket = serverSocket.accept();

                        System.out.println("Connection from: " + socket);

                        SocketChannel socketChannel = socket.getChannel();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                    } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                        SocketChannel socketChannel = null;
                        socketChannel = (SocketChannel) key.channel();

                        boolean connection = readData(socketChannel, byteBuffer);

                        if (!connection) {
                            key.cancel();
                            Socket socket = null;
                            socket = socketChannel.socket();
                            socket.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void open() {
        ServerSocketChannel serverSocketChannel;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocket = serverSocketChannel.socket();

            InetSocketAddress address = new InetSocketAddress(port);
            serverSocket.bind(address);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server created on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean readData(SocketChannel socketChannel, ByteBuffer byteBuffer) {
        return true;
    }
}
