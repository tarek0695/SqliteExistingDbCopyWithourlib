package com.pixelhubllc.sqliteexistingdbwithourlib;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.pixelhubllc.sqliteexistingdbwithourlib.DbHelper.DatabaseHelper;
import com.pixelhubllc.sqliteexistingdbwithourlib.DbHelper.LoadDatabaseAsync;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    static DatabaseHelper myDbHelper;
    static boolean databaseOpened=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
//        TextView tv = findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        ListView listView = findViewById(R.id.list_view);
        myDbHelper = new DatabaseHelper(this);

        if(myDbHelper.checkDataBase())
        {
            openDatabase();

        }
        else
        {
            LoadDatabaseAsync task = new LoadDatabaseAsync(MainActivity.this);
            task.execute();
        }

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public static void openDatabase()
    {
        try {
            myDbHelper.openDataBase();
            databaseOpened=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}