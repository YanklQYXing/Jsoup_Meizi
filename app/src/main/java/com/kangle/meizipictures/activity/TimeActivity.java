package com.kangle.meizipictures.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kangle.meizipictures.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private Button pay_s;
    private TextView miyao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initView();
    }

    private void initView() {

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("生成秘钥");

        editText = (EditText) findViewById(R.id.pay_type);
        pay_s = (Button) findViewById(R.id.pay_s);
        miyao = (TextView) findViewById(R.id.miyao);
        pay_s.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_s:
                payString();
                break;
        }
    }

    // 生成秘钥
    private void payString() {
        String trim = editText.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            Toast.makeText(this, "请输入付款类型", Toast.LENGTH_SHORT).show();
            return;
        }else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 从今日开始
            Date date = null;
            long time = 0;
            try {
                date = formatter.parse("2018-01-01");
                time = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            miyao.setText(time+trim);
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(time+trim);
        }
    }
}
