package com.coder.guoy.goodtimes.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Version:
 * @Author:Guoy
 * @CreateTime:2017/10/24
 * @Descrpiton:段子
 */
public class EpisodeBean implements Parcelable {
    private String imageIcon;
    private String author;
    private String title;
    private String time;
    private String content;
    private String commnet;

    public EpisodeBean(String imageIcon, String author, String title, String time, String content,
                       String commnet) {
        this.imageIcon = imageIcon;
        this.author = author;
        this.title = title;
        this.time = time;
        this.content = content;
        this.commnet = commnet;
    }

    protected EpisodeBean(Parcel in) {
        imageIcon = in.readString();
        author = in.readString();
        title = in.readString();
        time = in.readString();
        content = in.readString();
        commnet = in.readString();
    }

    public static final Creator<EpisodeBean> CREATOR = new Creator<EpisodeBean>() {
        @Override
        public EpisodeBean createFromParcel(Parcel in) {
            return new EpisodeBean(in);
        }

        @Override
        public EpisodeBean[] newArray(int size) {
            return new EpisodeBean[size];
        }
    };

    public String getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(String imageIcon) {
        this.imageIcon = imageIcon;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommnet() {
        return commnet;
    }

    public void setCommnet(String commnet) {
        this.commnet = commnet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageIcon);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(content);
        dest.writeString(commnet);
    }
}
