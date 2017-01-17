package mariuszziolek.pl.gw2exchange.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.IOException;

import mariuszziolek.pl.gw2exchange.objects.Gem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class GW2API {

    private Service service;
    private static GW2API gw2API;

    private static final String URL = "https://api.guildwars2.com";

    private GW2API() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
    }

    public static GW2API getInstance() {
        if (gw2API == null)
            gw2API = new GW2API();

        return gw2API;
    }

    public void getCoinsToGemPriceAsync(final Context context) {
        Call<Gem> call = service.getCoinsToGemPrice();
        call.enqueue(new Callback<Gem>() {
            @Override
            public void onResponse(Call<Gem> call, Response<Gem> response) {
                if (response.isSuccessful()) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("GW2ExchangePreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("coins_to_gem_price", response.body().getCoinsPerGem());
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<Gem> call, Throwable t) {
                Toast.makeText(context, "Can't get information from Guild Wars 2 servers", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCoinsToGemPriceSync(Context context) {
        Call<Gem> call = service.getCoinsToGemPrice();
        try {
            Response<Gem> response = call.execute();
            if (response.isSuccessful()) {
                Gem gem = response.body();

                SharedPreferences sharedPreferences = context.getSharedPreferences("GW2ExchangePreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("coins_to_gem_price", gem.getPrice());
                editor.apply();

                return gem.getPrice();
            }
        } catch (IOException e) {
            Toast.makeText(context, "Can't get information from Guild Wars 2 servers", Toast.LENGTH_SHORT).show();
        }

        return null;
    }
}
