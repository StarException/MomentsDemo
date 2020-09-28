package com.thoughtworks.momentsdemo.net;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class GetUserProfile extends API {
  @Override
  protected String getUrl() {
    return "http://thoughtworks-ios.herokuapp.com/user/jsmith";
  }

  @Override
  protected Response parseJson(String json) {
    Response response = new Gson().fromJson(json,Response.class);
    return response;
  }

  public static class Response extends API.Response{
    @SerializedName("profile-image")
    public String profileImage;
    public String avatar;
    public String nick;
    public String username;
  }
}
