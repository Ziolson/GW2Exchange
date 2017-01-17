package mariuszziolek.pl.gw2exchange.rest;

import mariuszziolek.pl.gw2exchange.objects.Gem;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("v2/commerce/exchange/coins?quantity=1000000")
    Call<Gem> getCoinsToGemPrice();
}
