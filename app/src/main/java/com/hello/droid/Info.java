package com.hello.droid;

public class Info {

    private String mTitle;
    private String mSummary;

    public Info() {
        super();
    }

    public Info(String mTitle) {
        super();
        this.mTitle = mTitle;
    }

    public Info(String mTitle, String mSummary) {
        super();
        this.mTitle = mTitle;
        this.mSummary = mSummary;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        this.mSummary = summary;
    }

}
