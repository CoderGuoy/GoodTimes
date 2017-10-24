package com.coder.guoy.goodtimes.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Version:
 * @Author:Guoy
 * @CreateTime:2017/10/9
 * @Descrpiton:美眉
 */
public class GirlBean implements Parcelable {
    private String linkUrl;
    private String imageUrl;
    private String imgaeTitle;

    public GirlBean(String linkUrl, String imageUrl, String imgaeTitle) {
        this.linkUrl = linkUrl;
        this.imageUrl = imageUrl;
        this.imgaeTitle = imgaeTitle;
    }

    protected GirlBean(Parcel in) {
        linkUrl = in.readString();
        imageUrl = in.readString();
        imgaeTitle = in.readString();
    }

    public static final Creator<GirlBean> CREATOR = new Creator<GirlBean>() {
        @Override
        public GirlBean createFromParcel(Parcel in) {
            return new GirlBean(in);
        }

        @Override
        public GirlBean[] newArray(int size) {
            return new GirlBean[size];
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
