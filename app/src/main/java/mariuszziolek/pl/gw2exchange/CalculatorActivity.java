package mariuszziolek.pl.gw2exchange;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalculatorActivity extends AppCompatActivity {
    @BindView(R.id.calculatorGemsEditText)
    EditText calculatorGemsEditText;
    @BindView(R.id.calculatorCoinsTextView)
    TextView calculatorCoinsTextView;

    private double coinsToGemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);

        calculatorGemsEditText.setShowSoftInputOnFocus(false);

        this.coinsToGemPrice = getGemPriceFromSharedPref()/100;
    }

    public void updateEditTextOnClick(View view) {
        Button clickedButton = (Button) view;
        int currentEditTextValue = Integer.parseInt(calculatorGemsEditText.getText().toString());

        if (clickedButton.getText().toString().equals("←"))
            calculatorGemsEditText.setText(String.valueOf(currentEditTextValue/10));
        else {
            int value = Integer.parseInt(clickedButton.getText().toString());
            int amountOfGems = currentEditTextValue * 10 + value;
            calculatorGemsEditText.setText(String.valueOf(amountOfGems));
        }
        updateTextView(Integer.parseInt(calculatorGemsEditText.getText().toString()));
    }

    private void updateTextView(int amountOfGems) {
        double priceInCoins = coinsToGemPrice * amountOfGems;
        calculatorCoinsTextView.setText(String.format("%.2f", priceInCoins));
    }

    private double getGemPriceFromSharedPref() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("GW2ExchangePreferences", Context.MODE_PRIVATE);
        String coinsToGemPrice = sharedPreferences.getString("coins_to_gem_price", "");
        if (coinsToGemPrice.equals(""))
            return 0.0;

        return Double.parseDouble(coinsToGemPrice);
    }
}
