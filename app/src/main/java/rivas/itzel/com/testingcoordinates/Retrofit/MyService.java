package rivas.itzel.com.testingcoordinates.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

    public interface MyService {
        @POST("location")
        @FormUrlEncoded
        Observable<String> locationUser(@Field("latitude") double latitude,
                                        @Field("longitude") double longitude,
                                        @Field("timeStamp")long timeStamp);
        @POST("description")
        @FormUrlEncoded
        Observable<String> descriptionReports(@Field("latitude") double latitude,
                                              @Field("longitude") double longitude,
                                              @Field("timeStamp")long timeStamp,
                                              @Field("incident") String incident,
                                              @Field("description") String description,
                                              @Field("color") int color);
}
