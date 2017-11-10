package com.coder.guoy.goodtimes;

import android.os.Environment;

import java.io.File;

public class Constants {
    //https://www.meitulu.com/
    //http://www.182fl.com/		福利社
    //https://www.zhuangbi.info/hot
    public static final String WMPIC_URl = "http://www.wmpic.me/";
    //=================   zol  =====================
    //桌面壁纸
    public static final String ZOL_PC_URl = "http://desk.zol.com.cn/";
    public static final String ZOL_PC_DM = ZOL_PC_URl + "dongman/";
    //手机壁纸
    public static final String ZOL_URl = "http://sj.zol.com.cn/";
    public static final String ZOL_FJ = ZOL_URl + "bizhi/fengjing/";
    public static final String ZOL_MV = ZOL_URl + "bizhi/meinv/";
    public static final String ZOL_DM = ZOL_URl + "bizhi/dongman/";
    public static final String ZOL_CY = ZOL_URl + "bizhi/chuangyi/";
    public static final String ZOL_AQ = ZOL_URl + "bizhi/aiqing/";
    public static final String ZOL_KT = ZOL_URl + "bizhi/katong/";
    public static final String ZOL_KA = ZOL_URl + "bizhi/keai/";
    public static final String ZOL_MX = ZOL_URl + "bizhi/mingxing/";
    public static final String ZOL_YX = ZOL_URl + "bizhi/youxi/";
    public static final String ZOL_CM = ZOL_URl + "bizhi/chemo/";
    public static final String ZOL_TY = ZOL_URl + "bizhi/tiyu/";
    public static final String ZOL_JR = ZOL_URl + "bizhi/jieri/";
    public static final String ZOL_YS = ZOL_URl + "bizhi/yingshi/";
    public static final String ZOL_JZ = ZOL_URl + "bizhi/jianzhu/";
    public static final String ZOL_DW = ZOL_URl + "bizhi/dongwu/";
    public static final String ZOL_ZW = ZOL_URl + "bizhi/zhiwu/";

    //================= win400 =====================
    public static final String BASE_URl = "http://www.win4000.com/";
    public static final String IMG_URl = "http://pic1.win4000.com/";
    //桌面壁纸
    public static final String ZMBZ = BASE_URl + "wallpaper.html";
    //桌面壁纸-游戏壁纸
    public static final String ZMBZ_YXBZ = BASE_URl + "wallpaper_191_0_0_1.html";
    //桌面壁纸-卡通动漫
    public static final String ZMBZ_KTDM = BASE_URl + "wallpaper_192_0_0_1.html";
    //桌面壁纸-家居壁纸
    public static final String ZMBZ_JJBZ = BASE_URl + "wallpaper_193_0_0_1.html";
    //桌面壁纸-军事壁纸
    public static final String ZMBZ_JSBZ = BASE_URl + "wallpaper_194_0_0_1.html";
    //桌面壁纸-汽车壁纸
    public static final String ZMBZ_QCBZ = BASE_URl + "wallpaper_195_0_0_1.html";
    //桌面壁纸-广告壁纸
    public static final String ZMBZ_GGBZ = BASE_URl + "wallpaper_196_0_0_1.html";
    //桌面壁纸-设计创意
    public static final String ZMBZ_SJCY = BASE_URl + "wallpaper_197_0_0_1.html";
    //桌面壁纸-港台影视
    public static final String ZMBZ_GTYS = BASE_URl + "wallpaper_198_0_0_1.html";
    //桌面壁纸-欧美影视
    public static final String ZMBZ_OMYS = BASE_URl + "wallpaper_199_0_0_1.html";
    //桌面壁纸-日韩影视
    public static final String ZMBZ_RHYS = BASE_URl + "wallpaper_200_0_0_1.html";
    //桌面壁纸-大陆影视
    public static final String ZMBZ_DLYS = BASE_URl + "wallpaper_201_0_0_1.html";
    //桌面壁纸-港台明星
    public static final String ZMBZ_DTMX = BASE_URl + "wallpaper_202_0_0_1.html";
    //桌面壁纸-欧美明星
    public static final String ZMBZ_OMMX = BASE_URl + "wallpaper_203_0_0_1.html";
    //桌面壁纸-日韩明星
    public static final String ZMBZ_RHMX = BASE_URl + "wallpaper_204_0_0_1.html";
    //桌面壁纸-大陆明星
    public static final String ZMBZ_DLMX = BASE_URl + "wallpaper_205_0_0_1.html";
    //桌面壁纸-动物壁纸
    public static final String ZMBZ_DLDM = BASE_URl + "wallpaper_206_0_0_1.html";
    //桌面壁纸-高清壁纸
    public static final String ZMBZ_DWBZ = BASE_URl + "wallpaper_207_0_0_1.html";
    //桌面壁纸-风景壁纸
    public static final String ZMBZ_FJBZ = BASE_URl + "wallpaper_208_0_0_1.html";
    //桌面壁纸-植物壁纸
    public static final String ZMBZ_ZWBZ = BASE_URl + "wallpaper_209_0_0_1.html";
    //桌面壁纸-美女壁纸
    public static final String ZMBZ_MVBZ = BASE_URl + "wallpaper_2285_0_0_1.html";
    //桌面壁纸-日历壁纸
    public static final String ZMBZ_RLBZ = BASE_URl + "wallpaper_2286_0_0_1.html";
    //桌面壁纸-节日壁纸
    public static final String ZMBZ_JRBZ = BASE_URl + "wallpaper_2287_0_0_1.html";
    //桌面壁纸-体育壁纸
    public static final String ZMBZ_TYBZ = BASE_URl + "wallpaper_2357_0_0_1.html";
    //桌面壁纸-风格壁纸
    public static final String ZMBZ_FGBZ = BASE_URl + "wallpaper_2358_0_0_1.html";
    //桌面壁纸-美食壁纸
    public static final String ZMBZ_MSBZ = BASE_URl + "wallpaper_2361_0_0_1.html";
    //图片大全
    public static final String TPDQ = BASE_URl + "retu.html";
    //=================福利=====================
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

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GoodTimes" + File.separator + "Menu";
    //=================  ====================
    public static final int Home_Color = 100;
}
