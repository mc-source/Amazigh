package mchou.apps.amazigh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = findViewById(R.id.webview);
        findViewById(R.id.btnrequest).setOnClickListener(v->{
            new CallHttpAPI("a").execute();
        });

    }

    public class CallHttpAPI extends AsyncTask<String, String, String> {
        String word;
        public CallHttpAPI(String word){
            this.word=word;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
           /* RequestBody formBody = new FormBody.Builder()
                    .add("txt_terme", "maison")
                    .add("idlang","fr")
                    .build();*/
            Request request = new Request.Builder()
                    .url("https://fr.glosbe.com/fr/kab/histoire") //"https://tal.ircam.ma/dglai/lexieam.php")
                    //.post(formBody)
                    .build();


            try (Response response = client.newCall(request).execute()) {
                String data = response.body().string();
                return data;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "response..";
        }

        @Override
        protected void onPostExecute(String response) {
            //Toast.makeText(MainActivity.this, "data : "+response.length(), Toast.LENGTH_SHORT).show();
            Log.i("tests", "onPostExecute: "+response);

            try {
                String html = response; //new String(response, "UTF-8");
                String mime = "text/html";
                String encoding = "utf-8";

                myWebView.getSettings().setJavaScriptEnabled(false);
                myWebView.loadDataWithBaseURL(null, html, mime, encoding, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(response);
        }
    }
}
