package mariuszziolek.pl.gw2exchange.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import mariuszziolek.pl.gw2exchange.R;

public class CommonPriceListAdapter extends RecyclerView.Adapter<CommonPriceListAdapter.ViewHolder>{
    private Context context;
    private String[] values;
    private double gemPrice;

    public CommonPriceListAdapter(Context context, String[] objects) {
        this.context = context;
        this.values = objects;
        this.gemPrice = this.getGemPriceFromSharedPref();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewGems.setText(values[position]);
        double price = gemPrice * (Double.parseDouble(values[position])/100);
        holder.textViewCoins.setText(String.format("%.2f", price));
    }

    @Override
    public int getItemCount() {
        return values.length;
    }

//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.list_item, parent, false);
//        TextView textViewGems = (TextView) rowView.findViewById(R.id.textViewGemsInList);
//        TextView textViewCoins = (TextView) rowView.findViewById(R.id.textViewCoinsInList);
//
//        textViewGems.setText(values[position]);
//        double price = gemPrice * (Double.parseDouble(values[position])/100);
//        textViewCoins.setText(String.format("%.2f", price));
//
//        //return super.getView(position, convertView, parent);
//        return rowView;
//    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewGemsInList) TextView textViewGems;
        @BindView(R.id.textViewCoinsInList) TextView textViewCoins;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private double getGemPriceFromSharedPref() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GW2ExchangePreferences", Context.MODE_PRIVATE);
        String coinsToGemPrice = sharedPreferences.getString("coins_to_gem_price", "");
        if (coinsToGemPrice.equals(""))
            return 0.0;

        return Double.parseDouble(coinsToGemPrice);
    }
}
