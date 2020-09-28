package com.thoughtworks.momentsdemo;

import android.app.Application;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
  }

  public static OkHttpClient getHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder()
      .connectTimeout(50, TimeUnit.SECONDS)
      .sslSocketFactory(getSSLSocketFactory())
      .hostnameVerifier(getHostnameVerifier());
    return builder.build();
  }

  private static SSLSocketFactory getSSLSocketFactory() {
    try {
      SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, getTrustManagers(), new SecureRandom());
      return sslContext.getSocketFactory();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static TrustManager[] getTrustManagers() {
    return new TrustManager[]{new X509TrustManager() {
      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[]{};
      }
    }};
  }

  private static HostnameVerifier getHostnameVerifier() {
    return new HostnameVerifier() {
      @Override
      public boolean verify(String hostname, SSLSession session) {
        return true; // 直接返回true，默认verify通过
      }
    };
  }


}


