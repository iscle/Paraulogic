package me.iscle.paraulgic.data;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

import me.iscle.paraulgic.model.DatesJoc;
import me.iscle.paraulgic.model.Solucions;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParaulogicRepository {
    private static volatile ParaulogicRepository instance;

    private final OkHttpClient client;
    private final ParaulogicService paraulogicService;
    private final RodamotsService rodamotsService;

    private ParaulogicRepository() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        paraulogicService = new Retrofit.Builder()
                .baseUrl("https://paraulogic.iscle.me/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParaulogicService.class);

        rodamotsService = new Retrofit.Builder()
                .baseUrl("https://paraulogic.rodamots.cat/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RodamotsService.class);
    }

    public void getGameDates(Callback<DatesJoc> callback) {
        client.newCall(new Request.Builder()
                .url("https://paraulogic.rodamots.cat/")
                .get().build()).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    Element body = Jsoup.parse(response.body().string()).selectFirst("body");
                    String dataJoc = body.attr("data-joc");
                    String dataJocAnterior = body.attr("data-joc-ant");
                    callback.onSuccess(new DatesJoc(dataJoc, dataJocAnterior));
                } else {
                    callback.onError(new Exception("Error: " + response.code()));
                }
            }
        });
    }

    public void getSolucions(String date, Callback<Solucions> callback) {
        rodamotsService.getSolucions(date).enqueue(callback);
    }

    public static ParaulogicRepository getInstance() {
        if (instance == null) {
            synchronized (ParaulogicRepository.class) {
                if (instance == null) {
                    instance = new ParaulogicRepository();
                }
            }
        }
        return instance;
    }

    public static abstract class Callback<T> implements retrofit2.Callback<T> {
        public abstract void onSuccess(T body);

        public abstract void onError(Throwable t);

        @Override
        public void onResponse(@NonNull Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                onSuccess(response.body());
            } else {
                onError(new Exception("Error: " + response.code()));
            }
        }

        @Override
        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
            onError(t);
        }
    }
}
