package com.whut.umrhamster.gamesinfo.Model;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by 12421 on 2018/4/12.
 */

public class Spider3DMEvaluation {
    public void getHtml(Document document){
        List<BmobObject> list = new ArrayList<>();
        Elements elements = document.body().select("#QZmain").select("div.QZmainL").select("div.QZL1").select("div.QZlisttxt").select("li");
        Log.d("dsad",elements.text());
        for (Element element : elements){
            String title = element.select("p").select("a:nth-child(2)").text();
            Log.d("test",title);
            String[] numbers = element.select("p").select("a:nth-child(2)").attr("abs:href").split("/");
            long id = Long.valueOf(numbers[numbers.length-2]+numbers[numbers.length-1].substring(0,7));
            String link = element.select("p").select("a:nth-child(2)").attr("abs:href");
            Document subDocument = SpiderUtils.getDocument(link);
            Log.d("123",subDocument.body().select("#QZmain").select("div.QZmainL").select("div.arctitle").select("span").text());
            String[] desrip= subDocument.body().select("#QZmain").select("div.QZmainL").select("div.arctitle").select("span").text().split("发布时间：|作者：|编辑：");
            for (int j = 0;j<desrip.length;j++){
                Log.d("test",desrip[j]);
            }
            String pubdate = desrip[1].substring(0,17);
            String author = desrip[2];
            System.out.println(title+" "+id+" "+link+" "+pubdate+" "+author);
            GameEvaluation gameEvaluation = new GameEvaluation();
            gameEvaluation.setId(id);
            gameEvaluation.setTitle(title);
            gameEvaluation.setLink(link);
            gameEvaluation.setPubdate(BmobDate.createBmobDate("yyyy-MM-dd HH:mm:ss",pubdate+":00"));
            gameEvaluation.setAuthor(author);
            list.add(gameEvaluation);
        }
        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null){
                    for (int i=0; i<list.size(); i++){
                        BatchResult batchResult = list.get(i);
                        BmobException ex = batchResult.getError();
                        if (ex == null){
                            Log.d("Spider3DMEvaluation",i+"成功");
                        }else {
                            Log.d("Spider3DMEvaLuation",i+"失败");
                        }
                    }
                }else {
                    Log.d("Spider3DMEvaluation","失败");
                }
            }
        });
    }
}
