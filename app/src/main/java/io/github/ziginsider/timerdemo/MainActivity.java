package io.github.ziginsider.timerdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnCancel;
    TextView txtCounter;
    CheckBox optSingleShot;

    Timer timer;
    MyTimerTask myTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btn_start);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        optSingleShot = (CheckBox) findViewById(R.id.chkbx_singleshot);

        txtCounter = (TextView) findViewById(R.id.txtvw_counter);

        //myTimerTask = new MyTimerTask(this, txtCounter);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }

                timer = new Timer();
                myTimerTask = new MyTimerTask();

                if (optSingleShot.isChecked()) {
                    timer.schedule(myTimerTask, 1000); // 1 sec
                } else {
                    timer.schedule(myTimerTask, 1000, 1000); // delay 1 sec, repeat 5 sec
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
            }
        });
    }

    class MyTimerTask extends TimerTask {


        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
            final String strDate = simpleDateFormat.format(calendar.getTime());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtCounter.setText(strDate);
                }
            });
        }
    }
}
