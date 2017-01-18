package mariuszziolek.pl.gw2exchange;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mariuszziolek.pl.gw2exchange.rest.GW2API;
import mariuszziolek.pl.gw2exchange.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textViewCoins) TextView coinsTextView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;

    private SharedPreferences sharedPreferences;
    private GW2API gw2API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences("GW2ExchangePreferences", Context.MODE_PRIVATE);
        gw2API = GW2API.getInstance();

        coinsTextView.setText(sharedPreferences.getString("coins_to_gem_price", ""));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RefreshContent().execute();
            }
        });

        new RefreshContent().execute();
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
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshContent() {
        String coinsToGemPrice = gw2API.getCoinsToGemPriceSync(this);
        coinsTextView.setText(coinsToGemPrice);
    }

    private class RefreshContent extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(Void... params) {
            String coinsToGemPrice = gw2API.getCoinsToGemPriceSync(getApplicationContext());
            return coinsToGemPrice;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            coinsTextView.setText(s);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
