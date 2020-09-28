package com.thoughtworks.momentsdemo.item;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Moments {
  public String content;
  public ArrayList<Image> images;
  public Sender sender;
  public String error;//when error only have this filed
  public ArrayList<Comments> comments;

  public static class Image{
    public String url;
  }

  public static class Sender{
    public String username;
    public String nick;
    public String avatar;
  }

  public static class Comments{
    public String content;
    public Sender sender;
  }

  public boolean isInValid(){
    if (!TextUtils.isEmpty(error)){
      return true;
    }
    if (TextUtils.isEmpty(content)){
      if (images==null||images.size()==0){
        return true;
      }
    }
    return false;
  }
}
