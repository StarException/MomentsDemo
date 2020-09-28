package com.thoughtworks.momentsdemo.net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.thoughtworks.momentsdemo.item.Moments;

import java.util.ArrayList;


public class GetMomentsList extends API {
  @Override
  protected String getUrl() {
    return "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";
  }

  @Override
  protected Response parseJson(String json) {
    JsonParser jsonParser = new JsonParser();
    JsonArray jsonElements = jsonParser.parse(json).getAsJsonArray();
    Gson gson = new Gson();
    ArrayList<Moments> momentsSet = new ArrayList<>();
    for (JsonElement element:jsonElements){
      Moments moments = gson.fromJson(element,Moments.class);
      momentsSet.add(moments);
    }
    Response response = new Response();
    response.moments = momentsSet;
    return response;
  }


  public static class Response extends API.Response{
    public ArrayList<Moments> moments;
  }
}
