package rivas.itzel.com.testingcoordinates.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

    public interface MyService {
        @POST("location")
        @FormUrlEncoded
        Observable<String> locationUser(@Field("latitud") double latitud,
                                        @Field("longitud") double longitud,
                                        @Field("timeStamp")long timeStamp);

}
