package com.whut.umrhamster.gamesinfo;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by 12421 on 2018/4/6.
 */

public class Utils {
    public static String getDescription(){
        try {
            Log.d("11111111111111111111111","11111111111111111111111111111111111");
            Document document = Jsoup.connect("http://bmob-cdn-17808.b0.upaiyun.com/2018/04/05/487903754002bbef80b650e3b2aacceb.html").get();
            Log.d("dddddddd",document.body().text());
            return document.body().text();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ";";
    }
    public static String getDescription1(){
        return Jsoup.parse("http://bmob-cdn-17808.b0.upaiyun.com/2018/04/05/856c968b4081e16f809d1c3f33af8803.html").body().text();
    }
}
