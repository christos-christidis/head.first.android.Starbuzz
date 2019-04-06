package com.hfad.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion);
    }

    // SOS: this is how we avoid code duplication between onCreate and onUpgrade. Also note the right
    // way of applying successive updates until we reach the current version.
    // 2nd SOS: according to book, Android is hardwired to expect the _id field, so we include it.
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE drink(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "description TEXT, " +
                    "image_resource_id INTEGER);");

            insertDrink(db, "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and a steamed milk foam", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
        }

        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE drink ADD COLUMN favorite NUMERIC");
        }
    }

    private void insertDrink(SQLiteDatabase db, String name, String description, int resourceId) {
        // SOS: this is just a map from String names to values
        ContentValues rowValues = new ContentValues();
        rowValues.put("name", name);
        rowValues.put("description", description);
        rowValues.put("image_resource_id", resourceId);
        db.insert("drink", null, rowValues);
    }
}
