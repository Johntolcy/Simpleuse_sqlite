package com.example.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new MyDatabaseHelper(MainActivity.this, "BookStore.db", null, 2);
        Button button1 = (Button) findViewById(R.id.create_database);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.getWritableDatabase();
            }
        });

        Button button2 = (Button) findViewById(R.id.add_data);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "爱人之书");
                values.put("author", "刘重杨");
                values.put("pages", 999);
                values.put("price", 49.99);
                db.insert("Book", null, values);
                values.clear();

                values.put("name", "永恒之爱");
                values.put("author", "梁月");
                values.put("pages", 99);
                values.put("price", 19.99);
                db.insert("Book", null, values);

            }
        });

        Button button3 = (Button) findViewById(R.id.update_data);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 19.99);
                //第三个参数相当于where "?"相当于占位符，new String[]{}是对它的描述。
                db.update("Book", values, "name = ?", new String[]{"爱人之书"});
            }
        });

        Button button4 = (Button) findViewById(R.id.delete_data);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.delete("Book", "name = ?", new String[]{"爱人之书1"});
            }
        });

        Button button5 = (Button) findViewById(R.id.query_data);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "name " + name);
                        Log.d("MainActivity", "author " + author);
                        Log.d("MainActivity", "pages " + pages);
                        Log.d("MainActivity", "price " + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
