package com.coder.guoy.goodtimes;

import android.os.Environment;

import java.io.File;

public class Constants {
    //https://www.meitulu.com/
    //http://www.182fl.com/		福利社
    //http://www.mm2mm.com/
    public static final String WMPIC_URl = "http://www.wmpic.me/";

    //================= 优美   ======================
    public static final String URl = "http://www.umei.cc/";
    public static final String KTMH_URl = "bizhitupian/shoujibizhi/";

    //================= 福利社 =====================
    public static final String FL_URl = "http://www.182fl.com/";

    //头条女神
    public static final String TTNS_URl = "?cate=16";
    //尤果网
    public static final String TGW_URl = "?cate=6";
    //推女神
    public static final String TNS_URl = "?cate=2";
    //爱丝
    public static final String AS_URl = "?cate=24";
    //推女郎
    public static final String TNL_URl = "?cate=10";
    //========刊物=========
    //爱蜜社==
    public static final String AMS_URl = "?cate=5";
    //菠萝社==
    public static final String BLS_URl = "?cate=9";
    //美媛馆==
    public static final String MYG_URl = "?cate=11";
    //魅妍社==
    public static final String MYS_URl = "?cate=12";
    //模范学院==
    public static final String MFXY_URl = "?cate=17";
    //嗲囡囡==
    public static final String DNN_URl = "?cate=20";
    //爱秀==
    public static final String AX_URl = "?cate=22";
    //优星馆==
    public static final String YXG_URl = "?cate=28";
    //秀人网==
    public static final String XRW_URl = "?cate=29";
    //爱尤物==
    public static final String AYW_URl = "?cate=31";
    //青豆客==
    public static final String QDK_URl = "?cate=32";
    //猫萌榜==
    public static final String MMB_URl = "?cate=40";
    //克拉女神==
    public static final String KLNS_URl = "?cate=42";

    //===========================
    //蜜桃社
    public static final String MTS_URl = "?cate=3";
    //尤物馆
    public static final String YWG_URl = "?cate=4";
    //美腿宝贝
    public static final String MTBB_URl = "?cate=14";
    //DDY
    public static final String DDY_URl = "?cate=15";
    //影私荟
    public static final String YSH_URl = "?cate=18";
    //V女郎
    public static final String VNL_URl = "?cate=19";
    //星乐园
    public static final String XLY_URl = "?cate=21";
    //花の颜
    public static final String HDY_URl = "?cate=23";
    //兔几盟
    public static final String TJM_URl = "?cate=25";
    //顽味生活
    public static final String WWSH_URl = "?cate=26";
    //激萌文化
    public static final String JMWH_URl = "?cate=27";
    //飞图网
    public static final String FTW_URl = "?cate=30";
    //糖果画报
    public static final String TGHB_URl = "?cate=34";
    //御女郎
    public static final String YNL_URl = "?cate=35";
    //尤蜜荟
    public static final String YMH_URl = "?cate=36";
    //异思趣向
    public static final String YSQX_URl = "?cate=37";
    //薄荷叶
    public static final String BHY_URl = "?cate=38";
    //51MODO
    public static final String MODO_URl = "?cate=39";
    //果团
    public static final String GT_URl = "?cate=41";
    //花漾
    public static final String HY_URl = "?cate=43";


    //=================   zol  =====================
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
    //=================美女=====================
    //美女
    public static final String MM_URL = "http://m.xxxiao.com/";
    //男神
    public static final String GG_URL = "http://g.xxxiao.com/";
    //新鲜
    public static final String XX_URL = "http://nbsw.cc/";
    //热推
    public static final String RT_URL = "http://nbsw.cc/?resolved=unresolved";

    //最新套图_Girl1
    public static final String NEW = "/new/page/";
    //性感美女_Girl2
    public static final String XINGGAN = "/cat/xinggan/page/";
    //少女萝莉_Girl3
    public static final String SHAONV = "/cat/shaonv/page/";
    //美乳香臀_Girl4
    public static final String MRXT = "/cat/mrxt/page/";
    //丝袜美腿_Girl5
    public static final String SWMT = "/cat/swmt/page/";
    //性感特写_Girl6
    public static final String XGTX = "/cat/xgtx/page/";
    //欧美女神_Girl7
    public static final String OUMEI = "/cat/oumei/page/";
    //女神合集_Girl8
    public static final String COLLECTION = "/cat/collection/page/";
    //美女壁纸_Girl9
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
    public static final int FLS_TYPE = 301;
    public static final int MV_TYPE = 302;
}
