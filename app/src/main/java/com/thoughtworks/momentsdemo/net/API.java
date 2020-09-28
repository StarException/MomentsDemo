package com.thoughtworks.momentsdemo.net;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

abstract public class API {
  static public class Response{}
  public interface CallBack{
    void onSucceed();
    void onFail();
  }
  protected abstract String getUrl();
  protected abstract Response parseJson(String json);

  public void sendRequest(CallBack callBack){
    sendRequestImpl(callBack);
  }

  protected void sendRequestImpl(final CallBack callBack){
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder()
      .url(getUrl())
      .build();
    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        if (callBack!=null)
          callBack.onFail();
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
        if (callBack!=null){
          mResponse = parseJson(response.body().string());
          callBack.onSucceed();
        }
      }
    });
  }

  private Response mResponse = new Response();

  public <T extends Response> T getResponse(Class<T> clazz) {
    return clazz.cast(mResponse);
  }
}
