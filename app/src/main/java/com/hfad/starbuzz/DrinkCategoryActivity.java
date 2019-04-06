package com.hfad.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ListView listDrinks = findViewById(R.id.list_drinks);

        // SOS: ArrayAdapter calls toString() on each drink!
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Drink.drinks);
        listDrinks.setAdapter(listAdapter);

        // SOS: 1st item has id = 0 and position 0 or 1, depending on whether list has header.
        // If I use database, id will be the id of data in the db (which is why it's long...)
        listDrinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = DrinkActivity.newIntent(DrinkCategoryActivity.this, (int) id);
                startActivity(intent);
            }
        });
    }
}
