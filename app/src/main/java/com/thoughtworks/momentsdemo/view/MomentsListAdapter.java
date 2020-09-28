package com.thoughtworks.momentsdemo.view;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thoughtworks.momentsdemo.R;
import com.thoughtworks.momentsdemo.item.Moments;
import com.thoughtworks.momentsdemo.item.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MomentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


  public static class MomentsViewHolder extends RecyclerView.ViewHolder {

    public MomentsViewHolder(@NonNull View itemView) {
      super(itemView);
      senderAvatar = itemView.findViewById(R.id.sender_avatar_iv);
      senderName = itemView.findViewById(R.id.sender_nike_tv);
      momentsContent = itemView.findViewById(R.id.moments_content_tv);
      momentsImagesRv = itemView.findViewById(R.id.moments_images_list_rv);
      commentRv = itemView.findViewById(R.id.moments_comments_list_rv);
    }

    public ImageView senderAvatar;
    public TextView senderName;
    public TextView momentsContent;
    public RecyclerView momentsImagesRv;
    public RecyclerView commentRv;
  }

  public static class ErrorViewHolder extends RecyclerView.ViewHolder {

    public ErrorViewHolder(@NonNull TextView itemView) {
      super(itemView);
      errorText = itemView;
    }

    public TextView errorText;
  }


  public static class ContentImagesAdapter extends RecyclerView.Adapter<ContentImagesAdapter.ContentImagesViewHolder> {

    public static class ContentImagesViewHolder extends RecyclerView.ViewHolder {

      public ContentImagesViewHolder(@NonNull View view) {
        super(view);
        contentImage = view.findViewById(R.id.view_holder_image);
      }

      public ImageView contentImage;
    }


    private ArrayList<Moments.Image> mImagesSet;

    public ContentImagesAdapter(ArrayList<Moments.Image> mImagesSet) {
      if (mImagesSet == null) {
        mImagesSet = new ArrayList<>(0);
      }
      this.mImagesSet = mImagesSet;
    }

    @NonNull
    @Override
    public ContentImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_viewholder_image, parent, false);

      return new ContentImagesViewHolder(holderView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentImagesViewHolder holder, int position) {
      Moments.Image image = mImagesSet.get(position);
      Log.d("image",image.url);
      Glide.with(holder.itemView)
        .load(image.url)
        .error(R.drawable.ic_launcher_background)
        .placeholder(R.drawable.ic_launcher_background)
        .centerCrop()
        .into(holder.contentImage);
    }

    @Override
    public int getItemCount() {
      return mImagesSet.size();
    }


  }

  public static class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {

      public CommentsViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.comment_sender_name);
        content = itemView.findViewById(R.id.comment_sender_content);
      }

      public TextView name;
      public TextView content;
    }

    private ArrayList<Moments.Comments> mCommentsSet;

    public CommentsAdapter(ArrayList<Moments.Comments> mCommentsSet) {
      if (mCommentsSet==null){
        mCommentsSet = new ArrayList<>(0);
      }
      this.mCommentsSet = mCommentsSet;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View holderView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_viewholer_comment, parent, false);
      return new CommentsViewHolder(holderView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
      Moments.Comments comments = mCommentsSet.get(position);
      holder.name.setText(comments.sender.nick+":");
      holder.content.setText(comments.content);
    }

    @Override
    public int getItemCount() {
      return mCommentsSet.size();
    }
  }


  public static class HeaderHolder extends RecyclerView.ViewHolder{

    public HeaderHolder(@NonNull View itemView) {
      super(itemView);
      userName = itemView.findViewById(R.id.user_name);
      userAvatar = itemView.findViewById(R.id.user_avatar);
    }
    public TextView userName;
    public ImageView userAvatar;
  }

  public static class LoadMoreFooter extends RecyclerView.ViewHolder{

    public LoadMoreFooter(@NonNull View itemView) {
      super(itemView);
    }
    public View contentView;
  }


  private ArrayList<Moments> mDataSet;

  public MomentsListAdapter(ArrayList<Moments> mDataSet) {
    this.mDataSet = mDataSet;
  }

  public void setmDataSet(ArrayList<Moments> mDataSet) {
    this.mDataSet = mDataSet;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType==type_header){
      View headerView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_moments_header,parent,false);
      return new HeaderHolder(headerView);
    }
    if (viewType==type_footer){
      View footerView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_moments_loadmore_footer,parent,false);
      this.footerView = footerView;
      footerView.setVisibility(View.GONE);
      return new LoadMoreFooter(footerView);
    }
    View holderView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.layout_viewholder_moments, parent, false);
    return new MomentsViewHolder(holderView);

  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (getItemViewType(position)==type_header){
      if (user==null){
        return;
      }
      HeaderHolder headerHolder = (HeaderHolder) holder;
      headerHolder.userName.setText(user.nick);
      Glide.with(headerHolder.itemView.getContext())
        .load(user.avatar).into(headerHolder.userAvatar);

      return;
    }
    if (getItemViewType(position)==type_footer){
      return;
    }
    Moments moments = mDataSet.get(position-1);
    MomentsViewHolder momentsViewHolder = (MomentsViewHolder) holder;
    if (moments.sender!=null){
      if (!TextUtils.isEmpty(moments.sender.nick)){
        momentsViewHolder.senderName.setText(moments.sender.nick);
      }
      if (!TextUtils.isEmpty(moments.sender.avatar)) {
        Glide.with(holder.itemView).load(moments.sender.avatar)
          .error(R.mipmap.ic_launcher)
          .placeholder(R.mipmap.ic_launcher)
          .centerCrop()
          .into(momentsViewHolder.senderAvatar);
      }
    }



    momentsViewHolder.momentsContent.setVisibility(TextUtils.isEmpty(moments.content) ? View.GONE : View.VISIBLE);
    momentsViewHolder.momentsContent.setText(moments.content);

    NineGirdLayoutManager momentsImagesLayoutManager = new NineGirdLayoutManager(holder.itemView.getContext());
    momentsViewHolder.momentsImagesRv.setLayoutManager(momentsImagesLayoutManager);
    ContentImagesAdapter contentImagesAdapter = new ContentImagesAdapter(moments.images);
    momentsViewHolder.momentsImagesRv.setAdapter(contentImagesAdapter);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext()){
      @Override
      public boolean canScrollVertically() {
        return false;
      }
    };
    CommentsAdapter commentsAdapter = new CommentsAdapter(moments.comments);
    momentsViewHolder.commentRv.setLayoutManager(linearLayoutManager);
    momentsViewHolder.commentRv.setAdapter(commentsAdapter);
  }

  private final int type_normal = 0;
  private final int type_header = 1;
  private final int type_footer = 2;
  private View footerView;

  @Override
  public int getItemViewType(int position) {
    if (position==0){
      return type_header;
    }
    if (position>=getItemCount()-1){
      return type_footer;
    }
    return type_normal;
  }

  private User user;

  public void setUser(User user){
    this.user = user;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return mDataSet.size()+2;
  }


  public void setLoadMore(){
    footerView.setVisibility(View.VISIBLE);
  }

  public void setLoadMoreData(ArrayList<Moments> mDataSet){
    this.mDataSet.addAll(mDataSet);
    footerView.setVisibility(View.GONE);
    notifyDataSetChanged();
  }

}
