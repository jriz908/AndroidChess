package com.example.jar424.chess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import app.Game;

public class RecordActivity extends AppCompatActivity {


    private ListView list;
    private Switch sort;
    private static String s;
    private ArrayList<String> files;
    private boolean sortByName;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity);

        sort = (Switch) findViewById(R.id.sort);
        sort.setText("Sort by Name");

        initialize_list();
        s = null;

        if (files == null || files.size() < 1)
            sort.setEnabled(false);

        sortByName = false;

    }

    private void initialize_list () {
        list = (ListView) findViewById(R.id.listview);
        files = new ArrayList<>(Arrays.asList(fileList()));
        files.remove(0);

        if (files == null || files.size() < 1)
            return;

        sort.setEnabled(true);

        sortByDate();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, files);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                String temp = (String) list.getItemAtPosition(position);

                if (s != null) {
                    if (temp.equals(s)) {
                        adapter.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                        s = null;
                    }
                } else {
                    s = temp;
                    adapter.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                }
            }
        });
    }

    public void switchSort(View v){

        sortByName = !sortByName;

        if(sortByName){
            sortByName();
        }else{
            sortByDate();
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, files);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
    }

    private void sortByDate(){

        Collections.sort(files, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {

                String dateString1 = s1.substring(s1.lastIndexOf("\t") + 1);
                String dateString2 = s2.substring(s2.lastIndexOf("\t") + 1);

                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

                Date date1 = null;
                try {
                    date1 = format.parse(dateString1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date2 = null;
                try {
                    date2 = format.parse(dateString2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (date1.compareTo(date2) <= 0) {
                    return -1;
                }else{
                    return 1;
                }


            }
        });

    }

    private void sortByName(){

        Collections.sort(files);

    }

    private void openDialog(String s){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert Dialog");

        // Setting Dialog Message
        alertDialog.setMessage(s);


        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                //System.out.println("Error acknowledged");
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void playback (View v) {

        if (s == null) {
            openDialog("No Game selected");
            return;
        }

        Intent intent = new Intent(this, PlaybackActivity.class);
        startActivity(intent);
    }

    public void remove (View v) {

        if (s == null) {
            openDialog("No Game selected");
            return;
        }

        deleteFile(s);
        files.remove(s);
        s = null;

        if (files == null || files.size() < 1)
            sort.setEnabled(false);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, files);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
    }

    public static String getSelected () {
        return s;
    }

}
