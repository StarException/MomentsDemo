package com.thoughtworks.momentsdemo.item;

import java.util.ArrayList;
import java.util.List;

public class StepMoments {
  private final int step = 5;
  private int start = 0;
  private int end;
  private ArrayList<Moments> sourceSet;
  private boolean canGetStepSet = false;

  public StepMoments(ArrayList<Moments> sourceSet) {
    this.sourceSet = sourceSet;
  }

  public ArrayList<Moments> getStepSet(){
    end = start+step;
    end = Math.min(end, sourceSet.size());
    canGetStepSet = end<sourceSet.size();
    ArrayList<Moments> moments = new ArrayList<>();
    moments.addAll(sourceSet.subList(start,end));
    start = end;
    return moments;
  }

  public boolean isCanGetStepSet() {
    return canGetStepSet;
  }
}
