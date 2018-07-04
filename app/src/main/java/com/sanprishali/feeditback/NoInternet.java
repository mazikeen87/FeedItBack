package com.sanprishali.feeditback;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NoInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        onInternetActivity();
    }
    protected void onInternetActivity(){
        NoInternet.internetCheck task = new NoInternet.internetCheck();
        task.execute();
    }
    private class internetCheck extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                sock.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                //move to the main page
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Intent mainIntent = new Intent(NoInternet.this, MainActivity.class);
                        if (!NoInternet.this.isFinishing()) {
                            NoInternet.this.startActivity(mainIntent);
                            NoInternet.this.finish();
                        }
                    }
                },  100);
            }else{
                onInternetActivity();
            }
        }
    }
}
