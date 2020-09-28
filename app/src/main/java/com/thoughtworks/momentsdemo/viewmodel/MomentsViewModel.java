package com.thoughtworks.momentsdemo.viewmodel;

import android.app.Application;

import com.thoughtworks.momentsdemo.item.Moments;
import com.thoughtworks.momentsdemo.item.User;
import com.thoughtworks.momentsdemo.net.API;
import com.thoughtworks.momentsdemo.net.GetMomentsList;
import com.thoughtworks.momentsdemo.net.GetUserProfile;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


public class MomentsViewModel extends AndroidViewModel {
  private MutableLiveData<ArrayList<Moments>> mDataSet = new MutableLiveData<>();
  private MutableLiveData<User> mUserData = new MutableLiveData<>();

  public MomentsViewModel(@NonNull Application application) {
    super(application);
  }


  public void getMomentsList(){
    final GetMomentsList getMomentsList = new GetMomentsList();
    getMomentsList.sendRequest(new API.CallBack() {
      @Override
      public void onSucceed() {
        GetMomentsList.Response response = getMomentsList.getResponse(GetMomentsList.Response.class);
        mDataSet.postValue(response.moments);
      }

      @Override
      public void onFail() {

      }
    });
  }


  public void getUserProfile(){
    final GetUserProfile getUserProfile = new GetUserProfile();
    getUserProfile.sendRequest(new API.CallBack() {
      @Override
      public void onSucceed() {
        GetUserProfile.Response response = getUserProfile.getResponse(GetUserProfile.Response.class);
        User user = new User();
        user.avatar = response.avatar;
        user.nick = response.nick;
        user.profileImage = response.profileImage;
        user.userName = response.username;
        mUserData.postValue(user);
      }

      @Override
      public void onFail() {

      }
    });
  }

  public MutableLiveData<ArrayList<Moments>> getDataSet() {
    return mDataSet;
  }

  public MutableLiveData<User> getUserData() {
    return mUserData;
  }
}
