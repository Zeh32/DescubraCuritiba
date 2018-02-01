package com.example.jgaug.descubracuritiba.Api;

/**
 * Created by eadcn on 29/01/2018.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.example.jgaug.descubracuritiba.Api.endpoint.distanciaApi;

import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by diogo on 12/22/16.
 */

public class DescubraCuritibaApi {
    public static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;
    private Map<String, Object> mServicesPool = new HashMap<>();
    public static String URL_BASE = "https://maps.googleapis.com/";

    public DescubraCuritibaApi() {
        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
//                builder.header("Content-Type", "application/x-www-form-urlencoded");
                builder.header("Content-Type", "application/json");
//                String token = SharedPreferencesUtils.getInstance().getValue(DimmiConstants.USER_TOKEN, String.class);
//                if (!StringUtils.isNullOrEmpty(token))
//                    builder.header("Authorization", "Bearer " + token);
                // Get JSON response
                return chain.proceed(builder.build());
            }
        };

        // Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add the interceptor to OkHttpClient
        mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        // GSON Parser
        Gson gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .create();

        // Build the retrofit object
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mHttpClient)
                .build();
    }

    public DescubraCuritibaApi(String URL_BASE) {
        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
//                builder.header("Content-Type", "application/x-www-form-urlencoded");
                builder.header("Content-Type", "application/json");
//                String token = SharedPreferencesUtils.getInstance().getValue(DimmiConstants.USER_TOKEN, String.class);
//                if (!StringUtils.isNullOrEmpty(token))
//                    builder.header("Authorization", "Bearer " + token);
                // Get JSON response
                return chain.proceed(builder.build());
            }
        };

        // Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add the interceptor to OkHttpClient
        mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        // GSON Parser
        Gson gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .create();

        // Build the retrofit object
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mHttpClient)
                .build();
    }

    /**
     * Creates a service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        String key = service.getSimpleName();
        if (!mServicesPool.containsKey(key)) {
            mServicesPool.put(key, mRetrofit.create(service));
        }
        return (T) mServicesPool.get(key);
    }

    public String getBaseUrl() {
        return mRetrofit.baseUrl().url().toString();
    }

    public distanciaApi distanciaApi() {
        return create(distanciaApi.class);
    }

    public static class DateTimeTypeConverter
            implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
        @Override
        public JsonElement serialize(DateTime src, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                return new DateTime(json.getAsString());
            } catch (IllegalArgumentException e) {
                // May be it came in formatted as a java.util.Date, so try that
                Date date = context.deserialize(json, Date.class);
                return new DateTime(date);
            }
        }
    }
}
