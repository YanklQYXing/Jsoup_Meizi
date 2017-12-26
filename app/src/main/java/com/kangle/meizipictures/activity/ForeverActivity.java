package com.kangle.meizipictures.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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

import com.kangle.firstarticle.utils.MyLog;
import com.kangle.firstarticle.utils.PreferenceManager;
import com.kangle.meizipictures.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ForeverActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private Button pay_s;
    private TextView miyao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forever);
        initView();
    }

    private void initView() {

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("永久模式");


        editText = (EditText) findViewById(R.id.pay_type);
        pay_s = (Button) findViewById(R.id.pay_s);
        pay_s.setOnClickListener(this);
        miyao = (TextView) findViewById(R.id.miyao);

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
                openForever();
                break;
        }
    }

    private void openForever() {
        String trim = editText.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            Toast.makeText(this, "请输入商品说明", Toast.LENGTH_SHORT).show();
            return;
        }else {// 15147360000001
            if (trim.length()<=13){
                Toast.makeText(this, "请输入正确的商品说明", Toast.LENGTH_SHORT).show();
            }else {
                String substring = trim.substring(13);
                switch (substring){
                    case "1":
                        PreferenceManager.getInstance().setTypeNum(10);
                        break;
                    case "5":
                        PreferenceManager.getInstance().setTypeNum(50);
                        break;
                    case "10":
                        PreferenceManager.getInstance().setTypeNum(100);
                        break;
                    case "100":
                        PreferenceManager.getInstance().setTypeNum(10000);
                        break;
                    default:
                        Toast.makeText(this, "输入错误,请重新输入", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                        break;
                }
            }

        }
    }
}
