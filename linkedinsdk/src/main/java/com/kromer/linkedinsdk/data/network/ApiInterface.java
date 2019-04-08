package com.kromer.linkedinsdk.data.network;

import com.kromer.linkedinsdk.data.network.response.AccessTokenResponse;
import com.kromer.linkedinsdk.data.network.response.EmailResponse;
import com.kromer.linkedinsdk.data.network.response.LiteProfileResponse;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

  @FormUrlEncoded
  @POST("https://www.linkedin.com/uas/oauth2/accessToken")
  Single<AccessTokenResponse> getAccessToken(
      @Field("grant_type") String grant_type,
      @Field("code") String code,
      @Field("redirect_uri") String redirect_uri,
      @Field("client_id") String client_id,
      @Field("client_secret") String client_secret);

  @GET("https://api.linkedin.com/v2/me?projection=(id,firstName,lastName,profilePicture(displayImage~:playableStreams))")
  Flowable<LiteProfileResponse> getProfile(@Header("Authorization") String token);

  @GET("https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))")
  Flowable<EmailResponse> getEmail(@Header("Authorization") String token);
}