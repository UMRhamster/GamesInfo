package com.whut.umrhamster.gamesinfo;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whut.umrhamster.gamesinfo.Model.GameEvaluation;
import com.whut.umrhamster.gamesinfo.View.FragmentGameEvaluation;
import com.whut.umrhamster.gamesinfo.View.FragmentMain;
import com.whut.umrhamster.gamesinfo.View.FragmentManagement;

import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    //页面源
    private FragmentMain fragmentMain;
    private Fragment fragmentManagement;
    //
    private CoordinatorLayout coordinatorLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"ffd59e2845efa4494cc291a3e26baaf3");
        initView();
        setDefaultFragment();
    }

    protected void setDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_cdl,fragmentMain);
        fragmentTransaction.commit();
    }

    protected void initView(){
        fragmentMain = new FragmentMain();
        fragmentManagement = new FragmentManagement();
        navigationView = findViewById(R.id.main_ngv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.navi_menu_main:
                        if (fragmentMain == null){
                            fragmentMain = new FragmentMain();
                        }
                        fragmentTransaction.replace(R.id.main_cdl,fragmentMain);
                        fragmentTransaction.commit();
                        break;
                    case R.id.navi_menu_management:
                        if (fragmentManagement == null){
                            fragmentManagement = new FragmentManagement();
                        }
                        fragmentTransaction.replace(R.id.main_cdl,fragmentManagement);
                        fragmentTransaction.commit();
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                return true;
            }
        });
    }
}
