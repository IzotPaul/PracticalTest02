package eim.systems.cs.pub.ro.practicaltest;

/**
 * Created by student on 25.05.2018.
 */
        import android.util.Log;

        import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.HashMap;



public class MyServer extends Thread {
    private int port;
    private ServerSocket serverSocket;

    private HashMap<String, DataInfo> infoContainer;

    public MyServer(int port) {
        this.port = port;

        /*** Instantiate shared resource ***/
        this.infoContainer = new HashMap<>();

        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException ioexception) {
            Log.e(Constants.TAG, "Exceptie la crearea socketului - server");
        }
    }

    @Override
    public void run() {
        if (serverSocket == null) {
            Log.e(Constants.TAG, "Nu exista server socket");
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Log.i(Constants.TAG, "[SERVER THREAD] Waiting for a client invocation...");
                Socket socket = serverSocket.accept();

                Log.i(Constants.TAG, "[SERVER THREAD] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                MyServerClientHandler myServerClientHandler = new MyServerClientHandler(this, socket);
                myServerClientHandler.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public synchronized void setData(String city, DataInfo dataInfo) {
        this.infoContainer.put(city, dataInfo);
    }

    public synchronized DataInfo getData(String key) {
        return this.infoContainer.get(key);
    }
}
