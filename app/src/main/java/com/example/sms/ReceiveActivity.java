package com.example.sms;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiveActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static ReceiveActivity inst;
    ArrayList<String> smsList = new ArrayList<>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;

    public static ReceiveActivity instance() {
        return inst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        smsListView = (ListView) findViewById(R.id.listViewSMS);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);
        refreshInbox();
    }

    public void refreshInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor inboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = inboxCursor.getColumnIndex("body");
        int indexAddress = inboxCursor.getColumnIndex("address");
        int timeMillis = inboxCursor.getColumnIndex("date");
        Date date = new Date(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        String dateText = format.format(date);
        if (indexBody < 0 || !inboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = inboxCursor.getString(indexAddress) + " at " + "\n" + dateText+"\n" + inboxCursor.getString(indexBody)  + "\n";
            arrayAdapter.add(str);

        } while (inboxCursor.moveToNext());
    }

    public void updateList(final String sms) {
        arrayAdapter.insert(sms, 0);
        arrayAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            String[] sms = smsList.get(i).split("\n");
            String address = sms[0];
            String smsM = "";
            for (int j = 1; j < sms.length; j++) {
                smsM += sms[j] + "\n";
            }
            String strM = address + "\n";
            strM += smsM ;
            Toast.makeText(this, strM, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goToCompose(View v) {
        Intent intent = new Intent(ReceiveActivity.this, SendActivity.class);
        startActivity(intent);
    }
}
