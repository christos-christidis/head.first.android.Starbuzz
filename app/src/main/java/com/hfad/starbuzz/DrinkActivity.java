package com.hfad.starbuzz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    private static final String EXTRA_DRINK_ID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = getIntent().getIntExtra(EXTRA_DRINK_ID, -1);

        if (drinkId == -1) {
            Toast.makeText(this, "Can't get EXTRA_DRINK_ID from intent!", Toast.LENGTH_SHORT).show();
            finish();
        }

        Drink drink = Drink.drinks[drinkId];

        TextView name = findViewById(R.id.name);
        name.setText(drink.getName());

        TextView description = findViewById(R.id.description);
        description.setText(drink.getDescription());

        ImageView photo = findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());
    }

    static Intent newIntent(Context context, int itemClicked) {
        Intent intent = new Intent(context, DrinkActivity.class);
        intent.putExtra(EXTRA_DRINK_ID, itemClicked);
        return intent;
    }
}
