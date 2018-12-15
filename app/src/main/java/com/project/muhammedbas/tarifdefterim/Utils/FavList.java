package com.project.muhammedbas.tarifdefterim.Utils;

public class FavList {

    String category;
    long time;

    public FavList() {
    }

    public FavList(String category, long time) {
        this.category = category;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
