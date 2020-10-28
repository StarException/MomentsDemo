package com.thoughtworks.momentsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.thoughtworks.momentsdemo.item.Moments;
import com.thoughtworks.momentsdemo.item.StepMoments;
import com.thoughtworks.momentsdemo.item.User;
import com.thoughtworks.momentsdemo.view.MomentsListAdapter;
import com.thoughtworks.momentsdemo.viewmodel.MomentsViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private MomentsViewModel momentsViewModel_;
  private RecyclerView recyclerView_;
  private MomentsListAdapter momentsListAdapter_;
  private SwipeRefreshLayout swipeRefreshLayout_;
  private Handler handler_ = new Handler();
  private StepMoments stepMoments_;
  private boolean currentLoadMore_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    viewModelBindView();
    viewModelGetData();

  }

  private void initView(){
    recyclerView_ = findViewById(R.id.moments_list_rv);
    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    momentsListAdapter_ = new MomentsListAdapter(new ArrayList<Moments>());
    recyclerView_.setLayoutManager(linearLayoutManager);
    recyclerView_.setAdapter(momentsListAdapter_);
    recyclerView_.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState==RecyclerView.SCROLL_STATE_IDLE){
          if (linearLayoutManager.findLastVisibleItemPosition()==momentsListAdapter_.getItemCount()-1){
            if (!swipeRefreshLayout_.isRefreshing()){
              if (stepMoments_.isCanGetStepSet()){
                loadMore();
              }
            }
          }
        }
      }
    });
    swipeRefreshLayout_ = findViewById(R.id.sw);
    swipeRefreshLayout_.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        momentsViewModel_.getMomentsList();
      }
    });
  }

  private void viewModelBindView(){
    ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
    momentsViewModel_ =
      new ViewModelProvider(this,factory).get(MomentsViewModel.class);
    momentsViewModel_.getDataSet().observe(this, new Observer<ArrayList<Moments>>() {
      @Override
      public void onChanged(ArrayList<Moments> moments) {
        ArrayList<Moments> list = new ArrayList<>();
        for (Moments moments1 : moments){
          if (!moments1.isInValid()){
            list.add(moments1);
          }
        }
        swipeRefreshLayout_.setRefreshing(false);
        stepMoments_ = new StepMoments(list);
        momentsListAdapter_.setmDataSet(stepMoments_.getStepSet());
      }
    });
    momentsViewModel_.getUserData().observe(this, new Observer<User>() {
      @Override
      public void onChanged(User user) {
        momentsListAdapter_.setUser(user);
      }
    });
  }

  private void viewModelGetData(){
    momentsViewModel_.getMomentsList();
    swipeRefreshLayout_.setRefreshing(true);
    momentsViewModel_.getUserProfile();
  }

  private void loadMore(){
    if (currentLoadMore_){
      return;
    }
    currentLoadMore_ = true;
    momentsListAdapter_.setLoadMore();
    handler_.postDelayed(new Runnable() {
      @Override
      public void run() {
        currentLoadMore_ = false;
        momentsListAdapter_.setLoadMoreData(stepMoments_.getStepSet());
      }
    },2000L);
    Log.d("tag","需要合并");
    Log.d("tag","需要合并2");
  }
}