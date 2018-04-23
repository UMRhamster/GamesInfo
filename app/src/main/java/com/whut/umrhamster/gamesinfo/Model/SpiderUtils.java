package com.whut.umrhamster.gamesinfo.Model;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by 12421 on 2018/4/12.
 */

public class SpiderUtils {
    public static String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
    public static String ACCEPT_ENCODING = "gzip, deflate, sdch";
    public static String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8";
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";
    public static String CONNECTION = "keep-alive";
    //#QZmain > div.QZmainL > div.QZL1 > div.QZlisttxt
    public static Document getDocument(String url){
        try {
            Document document = Jsoup.connect("http://www.3dmgame.com/review/")
                    .header("User-Agent",USER_AGENT)
                    .header("Connection",CONNECTION)
                    .header("Accept-Language",ACCEPT_LANGUAGE)
                    .header("Accept-Encoding",ACCEPT_ENCODING)
                    .get();
            return document;
        }catch (IOException e){
            Log.d("SpideUtils","getHtml wrong");
            return null;
        }
    }
}
