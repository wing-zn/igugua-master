package com.constant;

/**
 * @author: Zhang Zhengliang
 * @create: 2018-06-21 16:28
 **/
public class RedisKeysPrefix {

    /**
     * 存储短信信息
     */
    public static String SMS_SEND_TIME_MESSAGE = "SMS_SEND_TIME_MESSAGE_:" + CommonConstant.project;

    //==================================图片验证码信息=============================
    /**
     * 存储图片验证码信息
     */
    public static String IMG_SEND_TIME_MESSAGE = "IMG_SEND_TIME_MESSAGE_:" + CommonConstant.project;


    /********************************redis 通道 监听***************************************************/
    /**
     * redis 通道汇率传送
     */
    public static String VB_XEX_CHANGE_RATE_CHANNEL = "vb:xExchangeRate:chan:";
    /**
     * redis 汇率数据
     * 添加单个币种 须追加code
     */
    public static String VB_XEX_CHANGE_RATE_DATA = "vb:indexTickerAll:usd2cny:";


    //=============================redis 消息队列数据 法币交易交易记录===========================================================
    /**
     * 法币交易交易记录实时数据合成
     */
    public static final String FROM_LINKED_QUEUE_NEW_DATE_MESSAGE = CommonConstant.project + "FROM:LINKED:QUEUE:NEW:DATE:MESSAGE:LAW:COIN:DEAL:";

    //=============================== 编辑 角色 / 用户信息 ========================================================
    /**
     * 存储编辑用户信息
     */
    public static final String ADMIN_USER_CACHE_EDIT_KEY = "edit:com.suda.admin.user.cache" + CommonConstant.project;
    public static final String APP_USER_CACHE_EDIT_KEY = "edit:com.suda.app.user.cache" + CommonConstant.project;
    public static final String AGENT_USER_CACHE_EDIT_KEY = "edit:com.suda.agent.user.cache" + CommonConstant.project;

    /**
     * 总管理后台角色权限信息
     */
    public static final String ADMIN_USER_ROLE_PERMISSION_EDIT_KEY = "edit:com.suda.admin.user.role.permission.cache" + CommonConstant.project;

    /**
     * 广告机主管理后台角色权限信息
     */
    public static final String AGENT_USER_ROLE_PERMISSION_EDIT_KEY = "edit:com.suda.agent.user.role.permission.cache" + CommonConstant.project;


    //==============================汽车大亨游戏 地块升级记录 stock_user_buildings_capacity_record===========================================
    /**
     * 用户升级存储记录
     */
    public static final String STOCK_USER_BUILDINGS_CAPACITY_RECORD_LISTS = CommonConstant.project + "stock:user:buildings:capacity:record:lists";
    /**
     * 用户升级每次产出时间存储
     */
    public static final String STOCK_USER_BUILDINGS_CAPACITY_OUTPUT_TIME_VALUE = CommonConstant.project + "stock:user:buildings:capacity:output:time:value";


    //=================================商城区域 KEY =============================================================

    public static final String SUDA_SHOP_AREA_KEY = CommonConstant.project + "suda:shop:area:key:shop";


    //=================================netty中存入channel KEY =============================================================

    public static final String NETTY_CHANNEL_KEY = CommonConstant.project + ":NETTY_CHANNEL_KEY";


    //================================redis 阻塞队列通知python 抓取数据========================================================================

    public static String START_SPIDER_DATA_CHANEL_ = "START_SPIDER_DATA_CHANEL_";

}

