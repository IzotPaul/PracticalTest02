package eim.systems.cs.pub.ro.practicaltest;

/**
 * Created by student on 25.05.2018.
 */


        import android.util.Log;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.PrintWriter;
        import java.net.Socket;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

        import cz.msebera.android.httpclient.client.HttpClient;
        import cz.msebera.android.httpclient.client.methods.HttpGet;
        import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
        import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class MyServerClientHandler extends Thread {

    private Socket socket;
    private MyServer server;

    //    SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");


    public MyServerClientHandler(MyServer server, Socket socket) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
//        super.run();
        String response = null;

        if (socket == null) {
            Log.e(Constants.TAG, "Socket null in server client handler");
            return;
        }

        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Buffered Reader / Print Writer are null!");
                return;
            }

            // TODO: Specific client handler implementation goes here
            String currentRequest = bufferedReader.readLine();

            String[] tokens = currentRequest.split(",");

            Log.i(Constants.TAG, currentRequest);

            if (tokens[0].equals("get")) {
                String key = tokens[1];

                Log.i(Constants.TAG, "GET: " + key);

                DataInfo dataInfo = server.getData(key);

                if (dataInfo == null) {
                    Log.i(Constants.TAG, "I got nothing");

                    return;
                }

                Log.i(Constants.TAG, "Take this: " + dataInfo.toString());

                printWriter.println(dataInfo);
                printWriter.flush();

            } else if (tokens[0].equals("put")) {
                String key = tokens[1];
                String value = tokens[2];

                Log.i(Constants.TAG, "PUT key=" + key + " value=" + value);

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(Constants.getUrl);

                String jsonStr = httpClient.execute(httpGet, new BasicResponseHandler());

                JSONObject obj = new JSONObject(jsonStr);
                String currentDate = obj.getString(Constants.currentKey);

                currentDate = currentDate.replace("T" , " ");
                currentDate = currentDate.substring(0, 16);

                Log.i(Constants.TAG,  "Current date: " + currentDate);

                Date date = null;

                date = format.parse(currentDate);


                Log.i(Constants.TAG,  "Date prased: " + date.toString());

                DataInfo dataInfo = new DataInfo(date, value);
                server.setData(key, dataInfo);
            }
        } catch (Exception e) {
            Log.v(Constants.TAG, e.getMessage());

        } finally {
            try {
                socket.close();
            } catch (Exception e) {

            }
        }
    }
}
