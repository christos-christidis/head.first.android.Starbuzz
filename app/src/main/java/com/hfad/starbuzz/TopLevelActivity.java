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

public class TopLevelActivity extends Activity {

    private SQLiteDatabase mDb;
    private Cursor mFavoritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        setupListCategories();
        setupListFavorites();
    }

    private void setupListCategories() {
        ListView listCategories = findViewById(R.id.list_categories);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TopLevelActivity.this, "Not implemented!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        listCategories.setOnItemClickListener(itemClickListener);
    }

    private void setupListFavorites() {
        ListView listFavorites = findViewById(R.id.list_favorites);

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            mDb = starbuzzDatabaseHelper.getReadableDatabase();
            mFavoritesCursor = queryFavorites();

            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, mFavoritesCursor,
                    new String[]{"name"}, new int[]{android.R.id.text1}, 0);

            listFavorites.setAdapter(favoriteAdapter);
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = DrinkActivity.newIntent(TopLevelActivity.this, (int) id);
                startActivity(intent);
            }
        });
    }

    private Cursor queryFavorites() {
        return mDb.query("drink", new String[]{"_id", "name"},
                "favorite = ?", new String[]{Integer.toString(1)},
                null, null, "name");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        ListView listFavorites = findViewById(R.id.list_favorites);
        CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();

        Cursor newCursor = queryFavorites();
        adapter.changeCursor(newCursor);
        mFavoritesCursor = newCursor;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFavoritesCursor.close();
        mDb.close();
    }
}
