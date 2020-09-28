package com.thoughtworks.momentsdemo.item;

import com.google.gson.annotations.SerializedName;

public class User {
  @SerializedName("profile-image")
  public String profileImage;
  public String avatar;
  public String nick;
  @SerializedName("username")
  public String userName;
}
