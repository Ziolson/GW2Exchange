package mariuszziolek.pl.gw2exchange.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mariuszziolek.pl.gw2exchange.R;

public class CommonPriceListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;
    private double gemPrice;

    public CommonPriceListAdapter(Context context, String[] objects) {
        super(context, R.layout.list_item, objects);
        this.context = context;
        this.values = objects;
        this.gemPrice = this.getGemPriceFromSharedPref();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView textViewGems = (TextView) rowView.findViewById(R.id.textViewGemsInList);
        TextView textViewCoins = (TextView) rowView.findViewById(R.id.textViewCoinsInList);

        textViewGems.setText(values[position]);
        double price = gemPrice * (Double.parseDouble(values[position])/100);
        textViewCoins.setText(String.format("%.2f", price));

        //return super.getView(position, convertView, parent);
        return rowView;
    }

    private double getGemPriceFromSharedPref() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GW2ExchangePreferences", Context.MODE_PRIVATE);
        String coinsToGemPrice = sharedPreferences.getString("coins_to_gem_price", "");
        if (coinsToGemPrice != "")
            return Double.parseDouble(coinsToGemPrice);

        return 0.0;
    }
}
