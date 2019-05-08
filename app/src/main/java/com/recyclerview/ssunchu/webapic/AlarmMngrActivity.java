package com.recyclerview.ssunchu.webapic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlarmMngrActivity extends AppCompatActivity {
    
    Button btnAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_mngr);
        
        btnAlarm = findViewById(R.id.btnAlarm);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlert();
            }
        });
    }

    public void startAlert() {

       EditText text = findViewById(R.id.time);
       int parsetext = Integer.parseInt(text.getText().toString());
       Intent intent = new Intent(this, MyBroadcastReceiver.class);

       PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),22,intent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() +(parsetext * 1000), pendingIntent);

        Toast.makeText(this," Set in : " + parsetext,Toast.LENGTH_SHORT).show();

    }
}
