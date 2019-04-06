package com.hfad.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    private static final String EXTRA_DRINK_ID = "drinkId";

    static Intent newIntent(Activity activity, int id) {
        Intent intent = new Intent(activity, DrinkActivity.class);
        intent.putExtra(EXTRA_DRINK_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = getIntent().getIntExtra(EXTRA_DRINK_ID, -1);
        if (drinkId == -1) {
            Toast.makeText(this, "Can't get EXTRA_DRINK_ID", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("drink",
                    new String[]{"name", "description", "image_resource_id", "favorite"},
                    "_id = ?", new String[]{Integer.toString(drinkId)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = cursor.getInt(3) == 1;

                TextView name = findViewById(R.id.name);
                name.setText(nameText);

                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);

                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
            }

            // We're done w the database
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public void onFavoriteClicked(View view) {
        int drinkId = getIntent().getIntExtra(EXTRA_DRINK_ID, -1);
        if (drinkId == -1) {
            Toast.makeText(this, "Can't get EXTRA_DRINK_ID", Toast.LENGTH_SHORT).show();
            return;
        }

        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues rowValues = new ContentValues();
        rowValues.put("favorite", favorite.isChecked());

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update("drink", rowValues,
                    "_id = ?", new String[]{Integer.toString(drinkId)});

            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
}
