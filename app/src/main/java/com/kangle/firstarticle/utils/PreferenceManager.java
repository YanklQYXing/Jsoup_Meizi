package com.kangle.firstarticle.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/27.
 * sharePreference管理类
 */
public class PreferenceManager {

    public static final String SHARED_NAME = "share_name";
    private SharedPreferences mSharedPreferences;
    private static PreferenceManager mPreferenceManager;
    private SharedPreferences.Editor mEditor;

    // 要存储的设置名称
    // 登陆状态
    private String SHARED_KEY_SETTING_LOGIN_STATE = "shared_kay_string_login_state";
    private String SHARED_KEY_SETTING_IS_FIRST_OPEN = "shared_kay_string_is_first_open";
    private String SHARED_KEY_SETTING_USER_NAME = "shared_kay_string_user_name";
    private String SHARED_KEY_SETTING_USER_SEX = "shared_kay_string_user_sex";
    private String SHARED_KEY_SETTING_USER_ICON = "shared_kay_string_user_icon";
    private String SHARED_KEY_SETTING_USER_TYPE = "shared_kay_string_user_type";
    private String SHARED_KEY_SETTING_GET_CODE_TIME = "shared_kay_string_get_code_time";
    private String SHARED_KEY_SETTING_LATITUDE = "shared_kay_string_latitude";
    private String SHARED_KEY_SETTING_LONTITUDE = "shared_kay_string_lontitude";
    private String SHARED_KEY_SETTING_IS_NIGHT = "shared_kay_string_is_night";
    private String SHARED_KEY_SETTING_TEXT_SIZE = "shared_kay_string_text_size";
    private String SHARED_KEY_SETTING_DOWNLOAD_IN_WIFI = "shared_kay_string_download_in_wifi";
    private String SHARED_KEY_SETTING_GUID = "shared_kay_string_download_guid";
    private String SHARED_KEY_SETTING_MESSAGE_PUSH = "shared_kay_string_message_push";
    private String SHARED_KEY_SETTING_SCROLL_Y = "shared_kay_string_scroll_y";
    private String SHARED_KEY_SETTING_OPEN_CACHE = "shared_kay_string_open_cache";
    private String SHARED_KEY_SETTING_FUKUAN_CODE = "shared_kay_string_fukuan_code";
    private String SHARED_KEY_SETTING_MEIZI_CISHU = "shared_kay_string_meizi_cishu"; // 观看妹子的次数
    private String SHARED_KEY_SETTING_SHARE_NUM = "shared_kay_string_share_num"; // 分享次数
    private String SHARED_KEY_SETTING_TYPE_NUM = "shared_kay_string_type_num"; // 可观看妹子的次数

    public PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context context){
        if (mPreferenceManager==null){
            mPreferenceManager = new PreferenceManager(context);
        }
    }

    /**
     * 获取序列化PreferenceManager
     *
     * @return
     */
    public synchronized static PreferenceManager getInstance() {
        if (mPreferenceManager == null) {
            throw new RuntimeException("please init first!");
        }
        return mPreferenceManager;
    }

    public void setMeiziCishu(int c){
        mEditor.putInt(SHARED_KEY_SETTING_MEIZI_CISHU,c);
        mEditor.commit();
    }

    public int getMeiziCishu(){
        return mSharedPreferences.getInt(SHARED_KEY_SETTING_MEIZI_CISHU,0);
    }
    public void setTypeNum(int c){
        mEditor.putInt(SHARED_KEY_SETTING_TYPE_NUM,c);
        mEditor.commit();
    }

    public int getTypeNum(){
        return mSharedPreferences.getInt(SHARED_KEY_SETTING_TYPE_NUM,0);
    }
    public void setSharedNum(int c){
        mEditor.putInt(SHARED_KEY_SETTING_SHARE_NUM,c);
        mEditor.commit();
    }

    public int getSharedNum(){
        return mSharedPreferences.getInt(SHARED_KEY_SETTING_SHARE_NUM,0);
    }

    public void setFukuanCode(String code){
        mEditor.putString(SHARED_KEY_SETTING_FUKUAN_CODE,code);
        mEditor.commit();
    }

    public String getFukuanCode(){
        return mSharedPreferences.getString(SHARED_KEY_SETTING_FUKUAN_CODE,"");
    }

    /**
     * 登陆成功出设置
     *
     * @param b
     */
    public void setLoginState(boolean b) {
        mEditor.putBoolean(SHARED_KEY_SETTING_LOGIN_STATE, b);
        mEditor.commit();
    }

    public boolean getLoginState() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_LOGIN_STATE, false);
    }

    /**
     * 是否第一次打开
     * @param b
     */
    public void setIsFirstOpenApp(boolean b){
        mEditor.putBoolean(SHARED_KEY_SETTING_IS_FIRST_OPEN, b);
        mEditor.commit();
    }
    public boolean getIsFirstOpenApp() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_IS_FIRST_OPEN, false);
    }
    
    // 用户名
    public void  setUserName(String s){
        mEditor.putString(SHARED_KEY_SETTING_USER_NAME, s);
        mEditor.commit();
    }
    
    public String getUserName(){
        return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_NAME,"");
    }

    // 用户性别
    public void  setUserSex(int s){
        mEditor.putInt(SHARED_KEY_SETTING_USER_SEX, s);
        mEditor.commit();
    }

    public int getUserSex(){
        return mSharedPreferences.getInt(SHARED_KEY_SETTING_USER_SEX,-1);
    }
    
    // 用户头像
    public void  setUserIcon(String s){
        mEditor.putString(SHARED_KEY_SETTING_USER_ICON, s);
        mEditor.commit();
    }

    public String getUserIcon(){
        return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_ICON,"");
    }
    
    public int getUserType(){
        return mSharedPreferences.getInt(SHARED_KEY_SETTING_USER_TYPE,0);
    }
    // 用户头像
    public void  setUserType(int s){ // 0用户 1理发师
        mEditor.putInt(SHARED_KEY_SETTING_USER_TYPE, s);
        mEditor.commit();
    }
    
    // 发送验证码时记住当前时间
    public void setGetCodeTime(long l) {
        mEditor.putLong(SHARED_KEY_SETTING_GET_CODE_TIME, l);
        mEditor.commit();
    }

    public long getGetCodeTime() {
        return mSharedPreferences.getLong(SHARED_KEY_SETTING_GET_CODE_TIME, 0);
    }

    // 是否夜间模式
    public void setIsNight(boolean b){
        mEditor.putBoolean(SHARED_KEY_SETTING_IS_NIGHT, b);
        mEditor.commit();
    }

    public boolean getIsNight() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_IS_NIGHT, false);
    }

    public void setTextSize(int size){
        mEditor.putInt(SHARED_KEY_SETTING_TEXT_SIZE,size);
        mEditor.commit();
    }

    public int getTextSize(){
        return mSharedPreferences.getInt(SHARED_KEY_SETTING_TEXT_SIZE,Comment.MIDDLE);
    }

    public void setDownloadInWifi(boolean b){
        mEditor.putBoolean(SHARED_KEY_SETTING_DOWNLOAD_IN_WIFI,b);
        mEditor.commit();
    }

    public boolean getDownloadInWifi(){
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_DOWNLOAD_IN_WIFI,false);
    }
    public void setIsGuid(boolean b){
        mEditor.putBoolean(SHARED_KEY_SETTING_GUID,b);
        mEditor.commit();
    }

    public boolean getIsGuid(){
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_GUID,false);
    }
    public void setMessagePush(boolean b){
        mEditor.putBoolean(SHARED_KEY_SETTING_MESSAGE_PUSH,b);
        mEditor.commit();
    }

    public boolean getMessagePush(){
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_MESSAGE_PUSH,false);
    }

    public void setScrollY(int scrollY){
        mEditor.putInt(SHARED_KEY_SETTING_SCROLL_Y,scrollY);
        mEditor.commit();
    }

    public int getScrollY(){
        return mSharedPreferences.getInt(SHARED_KEY_SETTING_SCROLL_Y,0);
    }

    public void setOpenCache(boolean isOpen){
        mEditor.putBoolean(SHARED_KEY_SETTING_OPEN_CACHE,isOpen);
        mEditor.commit();
    }

    public boolean getOpenCache(){
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_OPEN_CACHE,true);
    }

}
