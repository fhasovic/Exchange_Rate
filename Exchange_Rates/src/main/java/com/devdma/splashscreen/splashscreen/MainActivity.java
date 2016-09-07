package com.devdma.splashscreen.splashscreen;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.devdma.splashscreen.R;
import com.devdma.splashscreen.pojo.Currency;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        TextView textUSD = (TextView) findViewById(R.id.valueUSD);
        TextView textEUR = (TextView) findViewById(R.id.valueEUR);

        Currency currency = (Currency) getIntent().getSerializableExtra("currency");

        textUSD.setText(String.valueOf(currency.getCurrencyUSD()));
        textEUR.setText(String.valueOf(currency.getCurrencyEUR()));

        // Import font from assets
        String fontPath = "fonts/Paint Peel Initials.ttf";
        TextView text = (TextView) findViewById(R.id.signature);
        // Font Face
        Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font to text
        text.setTypeface(typeface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
