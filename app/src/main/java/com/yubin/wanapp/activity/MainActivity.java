package com.yubin.wanapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.favourite.FavouriteActivity;
import com.yubin.wanapp.activity.guide.GuideFragment;
import com.yubin.wanapp.activity.home.HomeTabFragment;
import com.yubin.wanapp.activity.login.LoginActivity;
import com.yubin.wanapp.activity.search.SearchActivity;
import com.yubin.wanapp.data.UserManager;
import com.yubin.wanapp.util.DialogHelper;

import butterknife.BindView;


public class MainActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private TextView mUserTv;
    private Context mContext;
    private HomeTabFragment homeTabFragment;
    private GuideFragment guideFragment;
    private FragmentManager fragmentManager;
    private Fragment[] mFragments =new Fragment[2];
    private int curIndex;


    public static void show(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curIndex = 0;
        fragmentManager = getSupportFragmentManager();
        homeTabFragment = HomeTabFragment.newInstance();
        guideFragment = GuideFragment.newInstance();
        mFragments[0] = homeTabFragment;
        mFragments[1] = guideFragment;
        FragmentUtils.add(getSupportFragmentManager(),mFragments, R.id.frame_layout, curIndex);
        setTitle(R.string.home_label);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        //setTitle(R.string.home_label);
        mContext = this;
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(homeTabFragment.isActive()){
                    homeTabFragment.jumpToTop();
                }
                if(guideFragment.isActive()){
                    guideFragment.jumpToTop();
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        mUserTv = (TextView) navView.getHeaderView(0).findViewById(R.id.user_name);
        mUserTv.setText(UserManager.getUserManager().getUserName());
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_main:
                        showCurrentFragment(0);
                        setTitle(R.string.home_label);
                        break;
                    case R.id.nav_guide:
                        showCurrentFragment(1);
                        setTitle(R.string.guide_label);
                        break;
                    case R.id.nav_knowledge:

                        break;
                    case R.id.nav_project:
                        break;
                }
                return true;
            }
        });
    }

    private void showCurrentFragment(int index) {
        FragmentUtils.showHide(curIndex = index, mFragments);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            SearchActivity.show(mContext);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            DialogHelper.getConfirmDialog(mContext, mContext.getResources().getString(R.string.quit_message), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserManager.getUserManager().clearUserData();
                    LoginActivity.show(activityInstance);
                    finish();
                }
            }).show();

        } else if (id == R.id.nav_settings) {
            SettingsActivity.show(activityInstance);
        } else if (id == R.id.nav_about) {
            AboutActivity.show(activityInstance);
        } else if (id == R.id.nav_favourite) {
            FavouriteActivity.show(activityInstance);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void jumpToLogin() {
//        //登陆成功执行此逻辑
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
//    }
}
