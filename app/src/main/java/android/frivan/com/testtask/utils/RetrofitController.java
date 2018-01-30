package android.frivan.com.testtask.utils;

import android.frivan.com.testtask.interfaces.AnimalApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Для работы с Retrofit 2
 */

public class RetrofitController {
    private static final String BASE_ANIMAL_URL = "http://kot3.com/xim/";

    /**
     * Для животных
     */
    public static AnimalApi getAnimalApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_ANIMAL_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(AnimalApi.class);
    }

}