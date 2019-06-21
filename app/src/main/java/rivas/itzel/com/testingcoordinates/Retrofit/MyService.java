package rivas.itzel.com.testingcoordinates.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

    // Calls APIs created in NodeJS and the Collections that are going to be stored in the DB.
    public interface MyService {
        // 'location' is the name of the API where the attributes listed below are the variables that go inside the database collection
        @POST("location")
        @FormUrlEncoded
        Observable<String> locationUser(@Field("latitude") double latitude,
                                        @Field("longitude") double longitude,
                                        @Field("timeStamp")long timeStamp);
        // 'description' is the name of the API where the attributes listed below are the variables that go inside the database collection
        @POST("description")
        @FormUrlEncoded
        Observable<String> descriptionReports(@Field("latitude") double latitude,
                                              @Field("longitude") double longitude,
                                              @Field("timeStamp")long timeStamp,
                                              @Field("incident") String incident,
                                              @Field("description") String description,
                                              @Field("color") int color);
}
