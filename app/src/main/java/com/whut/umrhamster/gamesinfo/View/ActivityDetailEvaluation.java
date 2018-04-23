package com.whut.umrhamster.gamesinfo.View;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.umrhamster.gamesinfo.Model.GameEvaluation;
import com.whut.umrhamster.gamesinfo.R;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityDetailEvaluation extends AppCompatActivity {
    private WebView webView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView textViewTitle;
    private TextView textViewPubdate;
    private TextView textViewAuthor;
    private Toolbar toolbar;
    private ImageView imageViewBack;
    private ImageView imageViewShare;

    GameEvaluation gameEvaluation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_evaluation);
        initView();
        Intent intent = getIntent();
        gameEvaluation = (GameEvaluation) intent.getSerializableExtra("evaluation");
        textViewTitle.setText(gameEvaluation.getTitle());
        textViewAuthor.setText("作者："+gameEvaluation.getAuthor());
        textViewPubdate.setText("发布时间："+gameEvaluation.getPubdate().getDate());
        collapsingToolbarLayout.setTitle(gameEvaluation.getGamename());
//        Pattern pattern = Pattern.compile("《[\\s\\S]*》");
//        Matcher matcher = pattern.matcher(gameEvaluation.getTitle());
//        while (matcher.find()){
//            String string = matcher.group(0);
//            collapsingToolbarLayout.setTitle(string.substring(1,string.length()-1));
//        }
        webView.loadUrl("file:/data/user/0/com.whut.umrhamster.gamesinfo/cache/bmob/"+gameEvaluation.getId()+".html");
    }

    protected void initView(){
        webView = findViewById(R.id.detailEvaluation_acy_wv);
        collapsingToolbarLayout = findViewById(R.id.detailEvaluation_acy_ctl);
        textViewTitle = findViewById(R.id.detailEvaluation_acy_title);
        textViewPubdate = findViewById(R.id.detailEvaluation_acy_pubdate);
        textViewAuthor = findViewById(R.id.detailEvaluation_acy_author);
        toolbar = findViewById(R.id.detailEvaluation_acy_tb);
        imageViewBack = findViewById(R.id.detailEvaluation_acy_tb_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageViewShare = findViewById(R.id.detailEvaluation_acy_tb_share);
        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(gameEvaluation.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
    }
}
