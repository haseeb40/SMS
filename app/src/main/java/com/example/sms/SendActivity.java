package com.example.sms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendActivity extends AppCompatActivity {

    Button btnSend;
    EditText toPhoneNo;
    EditText etSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSend= (Button) findViewById(R.id.btnSend);
        toPhoneNo= (EditText) findViewById(R.id.editTextPhoneNo);
        etSMS= (EditText) findViewById(R.id.editTextSMS);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
    }
    public void sendSMS(){
        String toPhone = toPhoneNo.getText().toString();
        String SMS = etSMS.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhone,null,SMS,null,null);
            Toast.makeText(this,"Message Sent to " + toPhone,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void goToInbox(View v){
        Intent intent = new Intent(SendActivity.this,ReceiveActivity.class);
        startActivity(intent);
    }
}
