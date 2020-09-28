//package com.thoughtworks.momentsdemo.net;

//import android.content.Context;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.AppGlideModule;
//import com.thoughtworks.momentsdemo.MyApplication;
//
//import java.io.InputStream;
//
//import androidx.annotation.NonNull;
//@GlideModule
//public class HttpGlideModule extends AppGlideModule {
//  @Override
//  public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//    registry.replace(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(MyApplication.getHttpClient()));
//  }
//
//  @Override
//  public boolean isManifestParsingEnabled() {
//    return false;
//  }
//}
