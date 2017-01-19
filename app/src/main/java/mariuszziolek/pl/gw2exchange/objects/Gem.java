package mariuszziolek.pl.gw2exchange.objects;

import com.google.gson.annotations.SerializedName;

public final class Gem {

    @SerializedName("coins_per_gem")
    int coinsPerGem;

    int quantity;

    public int getCoinsPerGem() {
        return coinsPerGem;
    }

    public void setCoinsPerGem(int coinsPerGem) {
        this.coinsPerGem = coinsPerGem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        int gold = coinsPerGem / 100;
        int silver = coinsPerGem % 100;

        return gold + "." + silver;
    }
}
