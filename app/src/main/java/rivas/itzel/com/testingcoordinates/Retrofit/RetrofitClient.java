package rivas.itzel.com.testingcoordinates.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;


    public static Retrofit getInstance() {
        if(instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("http://10.10.2.45:3000") //an emulator, localhost will changed to 10.0.2.2
// itzel            .baseUrl("http://172.20.10.3:3000") //an emulator, localhost will changed to 10.0.2.2
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return instance;
    }
}