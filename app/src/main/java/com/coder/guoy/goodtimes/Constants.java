package com.coder.guoy.goodtimes;

import android.os.Environment;

import java.io.File;

public class Constants {

    //================= 电脑主题之家URL =====================
    public static final String BASE_URl = "http://www.win4000.com/";
    //首页
    public static final String HOME = BASE_URl + "retu.html";
    public static final String ANIME = BASE_URl + "wallpaper_192_0_0_1.html";
    //=================  =====================
    //美女
    public static final String MM_URL = "http://m.xxxiao.com/";
    //男神
    public static final String GG_URL = "http://g.xxxiao.com/";
    //新鲜
    public static final String XX_URL = "http://nbsw.cc/";
    //热推
    public static final String RT_URL = "http://nbsw.cc/?resolved=unresolved";
    //HOME
//    public static final String HOME = "page/";
    //最新套图+
//    public static final String NEW = "/new/page/";
    //性感美女+
    public static final String XINGGAN = "/cat/xinggan/page/";
    //少女萝莉+
    public static final String SHAONV = "/cat/shaonv/page/";
    //美乳香臀+
    public static final String MRXT = "/cat/mrxt/page/";
    //丝袜美腿+
    public static final String SWMT = "/cat/swmt/page/";
    //性感特写+
    public static final String XGTX = "/cat/xgtx/page/";
    //欧美女神+
    public static final String OUMEI = "/cat/oumei/page/";
    //女神合集+
    public static final String COLLECTION = "/cat/collection/page/";
    //美女壁纸+
    public static final String WALLPAPER = "/cat/wallpaper/page";
    //肌肉猛男+
    public static final String JRMN = "/cat/1/page/";
    //魅力型男+
    public static final String MLXN = "/cat/2/page/";
    //花美鲜肉+
    public static final String HMXR = "/cat/3/page/";
    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "code" + File.separator + "Menu";
}
