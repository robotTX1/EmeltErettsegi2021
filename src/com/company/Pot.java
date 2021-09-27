package com.company;

import java.util.Collections;
import java.util.List;

public class Pot {
    private final List<Integer> depthList;
    private final int startingIndex;
    private final int endingIndex;

    public Pot(List<Integer> depthList, int startingIndex, int endingIndex) {
        this.depthList = depthList;
        this.startingIndex = startingIndex;
        this.endingIndex = endingIndex;
    }

    public boolean gettingDeaper() {
        int centerDepthIndex = depthList.get(depthList.size() / 2);

        int max = 0;
        for(int i=0; i<centerDepthIndex; i++) {
            if(depthList.get(max) <= depthList.get(i)) {
                max = i;
            } else {
                return false;
            }
        }

        max = depthList.size()-1;

        for(int i = depthList.size()-1; i >= centerDepthIndex; i--) {
            if(depthList.get(max) < depthList.get(i)) {
                max = i;
            } else {
                return false;
            }
        }

        return true;
    }

    public int getWaterLevel() {
        return getVolume() - depthList.size() * 10;
    }

    public int getVolume() {
        int sum = 0;

        for(int i : depthList) {
            sum += i * 10;
        }

        return sum;
    }

    public int getDeapestPoint() {
        int max = Integer.MIN_VALUE;

        for(int i : depthList) {
            if(i > max) {
                max = i;
            }
        }

        return max;
    }

    public boolean insidePot(int index) {
        return index >= startingIndex && index < endingIndex;
    }

    //////////////////////////// Getters ////////////////////////////

    public List<Integer> getDepthList() {
        return Collections.unmodifiableList(depthList);
    }

    public int getStartingIndex() {
        return startingIndex + 1;
    }

    public int getEndingIndex() {
        return endingIndex + 1;
    }
}
