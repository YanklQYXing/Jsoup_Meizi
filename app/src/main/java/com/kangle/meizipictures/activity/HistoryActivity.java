package com.kangle.meizipictures.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import com.kangle.meizipictures.R;
import com.kangle.meizipictures.base.BaseActivity;
import com.kangle.meizipictures.fragment.CollectFragment;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
    }

    private void initView() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra("type", 0);
        if(intExtra==1){
            supportActionBar.setTitle("收藏");
        }else {
            supportActionBar.setTitle("浏览记录");
        }

//        titleText("浏览记录");
        CollectFragment collectFragment = new CollectFragment(intExtra);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_f,collectFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
