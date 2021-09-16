package kr.co.parthair.android.members.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import kr.co.parthair.android.members.net.api.ApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ClassName            RetrofitGenerator
 * Created by JSky on   2021-01-22
 * <p>
 * Description
 */
public class RetrofitGenerator {
    private static RetrofitGenerator instance = new RetrofitGenerator();

    public static RetrofitGenerator getInstance() {
        return instance;
    }

    public RetrofitGenerator() {
    }

    private static OkHttpClient createOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        builder.connectTimeout(5, TimeUnit.MINUTES);
        builder.readTimeout(5, TimeUnit.MINUTES);
        builder.writeTimeout(5, TimeUnit.MINUTES);
        return builder.build();
    }

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    // iamjsky
    static Retrofit retrofitPH = new Retrofit.Builder()
            .baseUrl("https://iamjsky.cafe24.com/mobile_app/parthair/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createOkHttpClient())
            .build();

    static ApiService apiService = retrofitPH.create(ApiService.class);

    public static ApiService getApiService() {
        return apiService;
    }

}
