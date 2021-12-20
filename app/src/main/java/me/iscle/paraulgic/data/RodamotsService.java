package me.iscle.paraulgic.data;

import me.iscle.paraulgic.model.Solucions;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RodamotsService {
    @GET("/")
    @Headers("Authorization: Basic Y29udHJhc2VueWE=")
    Call<Solucions> getSolucions(@Query("solucions") String solucions);
}
