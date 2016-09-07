package com.devdma.splashscreen.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.devdma.splashscreen.R;
import com.devdma.splashscreen.parser.JSONParser;
import com.devdma.splashscreen.pojo.Currency;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends Activity {

    // ссылка на получение курса валют с USD в ILS
    private final String URL_USD_TO_ILS =
            "http://api.fixer.io/latest?base=USD";
    // Ссылка на получение курса валют с EUR в ILS
    private final String URL_EUR_TO_ILS =
            "http://api.fixer.io/latest?symbols=ILS";

    // наш POJO куда мы поместим результат полученный по ссылкам
    private Currency currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        currency = new Currency();

        // запускаем поток на получение данных
        // именно в этот момент будет показываться Splash Screen
        // после завершения работы потока мы покажем MainActivity
        new PrefetchDataCurrency().execute();
    }

    // Создаем AsyncTask, который и будет выполнять получение
    // курса валют в потоке
    private class PrefetchDataCurrency extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // получаем курс USD в ILS с помощью нашего парсера
            JSONObject jsonUSDToILS = JSONParser.getJSONFromUrl(URL_USD_TO_ILS);
            // получаем курс EUR в ILS с помощью парсера
            JSONObject jsonEURToILS = JSONParser.getJSONFromUrl(URL_EUR_TO_ILS);

            // если мы что-то получили то заполняем наш currency полученными данными
            if (jsonUSDToILS != null && jsonEURToILS != null) {
                try {
                    // получаем с JSON значение по ключу rate
                    currency.setCurrencyEUR(jsonEURToILS.getJSONObject("rates").getDouble("ILS"));
                    currency.setCurrencyUSD(jsonUSDToILS.getJSONObject("rates").getDouble("ILS"));
                } catch (JSONException e) {
                    Log.e(PrefetchDataCurrency.class.getName(), "Not valid JSON data.");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // создаем новый Intent для перехода на MainActivity
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            // Добавляем в Intent наш объект
            // обратите внимание, что класс Currency должен реализовывать
            // интерфейс Serializable
            intent.putExtra("currency", currency);

            // запускаем новое Activity c Intent-ом, который хранит наш объект currency
            startActivity(intent);

            // завершаем работу потока
            finish();
        }
    }

}
