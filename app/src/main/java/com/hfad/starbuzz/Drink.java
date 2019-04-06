package com.hfad.starbuzz;

class Drink {

    private final String mName;
    private final String mDescription;
    private final int mImageResourceId;

    static final Drink[] drinks = {
            new Drink("Latte", "A couple of espresso shots with steamed milk", R.drawable.latte),
            new Drink("Cappuccino", "Espresso, hot milk and a steamed milk foam", R.drawable.cappuccino),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter),
    };

    private Drink(String name, String description, int imageResourceId) {
        mName = name;
        mDescription = description;
        mImageResourceId = imageResourceId;
    }

    String getName() {
        return mName;
    }

    String getDescription() {
        return mDescription;
    }

    int getImageResourceId() {
        return mImageResourceId;
    }

    @Override
    public String toString() {
        return getName();
    }
}
