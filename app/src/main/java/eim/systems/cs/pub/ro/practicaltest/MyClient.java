package eim.systems.cs.pub.ro.practicaltest;

        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.TextView;

        import java.io.BufferedReader;
        import java.io.PrintWriter;
        import java.net.Socket;


public class MyClient extends AsyncTask<String, String, String> {
    private TextView publishTextView;

    public MyClient(TextView publishTextView) {
        this.publishTextView = publishTextView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.publishTextView.setText("");
    }

    @Override
    protected String doInBackground(String... strings) {
        String address = strings[4];
        int port = Integer.parseInt(strings[3]);
        String operation = strings[2];

        String response = null;

        // TODO: Specific client operation goes here

        if (operation.equals("GET")) {
            String key = strings[3];

            try {
                Socket socket = new Socket(address, port);

                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);

                printWriter.println("get," + key);
                printWriter.flush();

                response = bufferedReader.readLine();


            } catch (Exception e) {
                Log.e(Constants.TAG, "Client socket err");
            }
        } else if (operation.equals("PUT")) {
            String key = strings[3];
            String value = strings[4];

            try {
                Socket socket = new Socket(address, port);

                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);

                printWriter.println("put," + key + "," + value);
                printWriter.flush();

            } catch (Exception e) {
                Log.e(Constants.TAG, "Client socket err");
            }


        }

//        try {
//            Socket socket = new Socket(address, port);
//
//            BufferedReader bufferedReader = Utilities.getReader(socket);
//            PrintWriter printWriter = Utilities.getWriter(socket);
//
//            // TODO: Specific client behaviour implementation here
//
//            printWriter.println(city);
//            printWriter.flush();
//
//            printWriter.println(attribute);
//            printWriter.flush();
//
//            return bufferedReader.readLine();
//        } catch (Exception e) {
//            Log.e(Constants.TAG, "Client socket err");
//        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        this.publishTextView.append(s + "\n");
    }
}

