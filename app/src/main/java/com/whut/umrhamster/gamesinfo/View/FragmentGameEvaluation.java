package com.whut.umrhamster.gamesinfo.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.whut.umrhamster.gamesinfo.Model.GameEvaluation;
import com.whut.umrhamster.gamesinfo.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 12421 on 2018/4/5.
 */

public class FragmentGameEvaluation extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<GameEvaluation> evaluationList;
    private List<GameEvaluation> evaluationListTemp;   //临时容器，主要用于解决swiperefreshlayout下拉后再上拉崩溃问题，以及避免刷新时的空白问题
    private EvaluationRVAdapter evaluationRVAdapter;
    //刷新
    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem;
    private Date date; //用于记录最后一条数据的时间（并不是获取数据的时间，而是在数据库中的时间）,用于控制查询
    private boolean isLoading;
    //
    private Handler mHandler = new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gameevaluation,container,false);
        initView(view);
        swipeRefreshLayout.setRefreshing(true);
        queryByTime(new Date(),"update"); //第一次进入时，自动根据当前时间进行数据获取
//        evaluationRVAdapter.notifyDataSetChanged();

        return view;
    }

    protected void initView(View view){
        //刷新
        swipeRefreshLayout = view.findViewById(R.id.gameEvaluation_fgm_srl);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorsrl));
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = view.findViewById(R.id.gameEvaluation_fgm_rv);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && !isLoading){
                    evaluationRVAdapter.changeShowStatus(0); //手指拖动状态
                }
                if (newState != RecyclerView.SCROLL_STATE_DRAGGING && lastVisibleItem+1 == evaluationRVAdapter.getItemCount() && !isLoading){
                    isLoading = true;
                    evaluationRVAdapter.changeShowStatus(1);
                    Toast.makeText(getActivity(),"触发上啦加载",Toast.LENGTH_SHORT).show();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            evaluationList.add(new GameEvaluation("测试标题1","测试测试测试测试","测试作者",new BmobDate(new Date())));
//                            evaluationList.add(new GameEvaluation("测试标题1","测试测试测试测试","测试作者",new BmobDate(new Date())));
                            queryByTime(date,"insert");
//                            evaluationRVAdapter.notifyDataSetChanged();
                        }
                    },500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                Log.d("FragmentGameEvaluation",""+dx+" "+dy);
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        evaluationList = new ArrayList<>();
        evaluationListTemp = new ArrayList<>();
        evaluationRVAdapter = new EvaluationRVAdapter(evaluationList,getActivity());
        evaluationRVAdapter.setOnItemClickListener(new EvaluationRVAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),ActivityDetailEvaluation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); //栈顶重复启动模式，防止快速点击打开多个activity
                intent.putExtra("evaluation",evaluationList.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(evaluationRVAdapter);
        isLoading = false; //初始为false,不处于加载
    }

    protected void queryByTime(Date date, final String queryMod){
        BmobQuery<GameEvaluation> bmobQuery = new BmobQuery<>();
        BmobDate currentDate = new BmobDate(date);
//        String time = "2018-04-04 00:00:00"; //测试
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date  = null;
//        try {
//            date = sdf.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        bmobQuery.addWhereLessThan("pubdate",currentDate);
        bmobQuery.setLimit(10);//限制最多返回十条记录，只加载九条，主要为了便于判断是否还有更多数据
        bmobQuery.order("-pubdate");//按照发布时间降序排序
        bmobQuery.findObjects(new FindListener<GameEvaluation>() {
            @Override
            public void done(List<GameEvaluation> list, BmobException e) {
                if (e == null){
                    int size= list.size();
                    if (size == 10){
                        size = size-1;   //只加入前九条数据，最后一条数据用于判断是否还有更多数据
                    }else if (size == 0){
                        evaluationRVAdapter.changeShowStatus(2);
                    }
                    for (int i=0;i<size;i++){
                        Log.d("list",list.get(i).getTitle());
                        downloadHtmlToCache(list.get(i),i,size,list.size(),queryMod);
                    }
                }
            }
        });
    }
    protected void downloadHtmlToCache(final GameEvaluation gameEvaluation, final int i, final int size, final int realSize, final String queryMod){
        if (size == 10 && i == 9){
            return; //size==10，说明还有更多数据，至少有一个，最后一条数据不插入，直接返回
        }
        BmobFile bmobFile = gameEvaluation.getJsonfile();
        bmobFile.download(new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                Log.d("download","download finished");
                if (e == null){
                    File file = new File(s);
                    try {
                        Document document = Jsoup.parse(file,"utf-8","");
                        gameEvaluation.setTest(document.body().text());
//                        evaluationList.add(gameEvaluation);
                        evaluationListTemp.add(gameEvaluation); //先将新数据保存在临时容器
                        if (i == size-1){
                            try {
//                                date = SimpleDateFormat.getDateTimeInstance().parse(gameEvaluation.getPubdate().getDate());
                                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(gameEvaluation.getPubdate().getDate());
//                                Log.d("test",date.toString());
                            }catch (ParseException ee){
                                ee.printStackTrace();
                            }
                            if (queryMod.equals("update")){
                                evaluationList.clear();  //当所有的新数据获取完毕后，再将主要容器数据清除
                            }else if(queryMod.equals("insert")){
//                                if (size != 10){ //如果获取到的数据不足十条，意味着没有更多的数据了，直接全部加入
//
//                                }
                            }
                            evaluationList.addAll(evaluationListTemp); //将临时容器数据转存到主要容器
                            evaluationListTemp.clear(); //再将临时容器数据清空，避免数据重复
                            evaluationRVAdapter.notifyDataSetChanged(); //列表更新，这样就同时解决先下拉刷新再上拉刷新的崩溃问题，以及刷新时的空白问题
                            swipeRefreshLayout.setRefreshing(false);
                            isLoading = false;
                            if (realSize != 10){
                                evaluationRVAdapter.changeShowStatus(2);
                            }
                        }
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                        Log.d("FragemntGameEvaluation","Jsoup parse fail");

                    }
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                queryByTime(new Date(),"update"); //根据当前时间进行刷新获取
            }
        });
    }
}
