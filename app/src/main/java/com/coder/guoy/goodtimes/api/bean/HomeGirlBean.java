package com.coder.guoy.goodtimes.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Version:
 * @Author:Guoy
 * @CreateTime:2017/10/9
 * @Descrpiton:
 */
public class HomeGirlBean implements Parcelable {
    private String linkUrl;
    private String imageUrl;
    private String imgaeTitle;

    public HomeGirlBean(String linkUrl, String imageUrl, String imgaeTitle) {
        this.linkUrl = linkUrl;
        this.imageUrl = imageUrl;
        this.imgaeTitle = imgaeTitle;
    }

    protected HomeGirlBean(Parcel in) {
        linkUrl = in.readString();
        imageUrl = in.readString();
        imgaeTitle = in.readString();
    }

    public static final Creator<HomeGirlBean> CREATOR = new Creator<HomeGirlBean>() {
        @Override
        public HomeGirlBean createFromParcel(Parcel in) {
            return new HomeGirlBean(in);
        }

        @Override
        public HomeGirlBean[] newArray(int size) {
            return new HomeGirlBean[size];
        }
    };

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImgaeTitle() {
        return imgaeTitle;
    }

    public void setImgaeTitle(String imgaeTitle) {
        this.imgaeTitle = imgaeTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(linkUrl);
        dest.writeString(imageUrl);
        dest.writeString(imgaeTitle);
    }
}
