package com.hfad.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends Activity {

    private SQLiteDatabase mDb;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ListView listDrinks = findViewById(R.id.list_drinks);

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            mDb = starbuzzDatabaseHelper.getReadableDatabase();
            // SUPER-SOS: mCursor MUST return _id if it's used by a cursor-adapter for a list. The
            // cursor-adapter will actually return _id as id in onItemClick below!!!
            mCursor = mDb.query("drink",
                    new String[]{"_id", "name"},
                    null, null, null, null, "name");

            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, mCursor,
                    new String[]{"name"}, new int[]{android.R.id.text1}, 0);

            listDrinks.setAdapter(listAdapter);
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        listDrinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = DrinkActivity.newIntent(DrinkCategoryActivity.this, (int) id);
                startActivity(intent);
            }
        });
    }

    // SOS: If there are many items, the cursor-adapter asks the mCursor only for the first few
    // (visible) items. If the user later scrolls down, it asks for the next items etc. Therefore, I
    // must keep mCursor & mDb open for all the duration that the adapter might use them...
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCursor.close();
        mDb.close();
    }
}
