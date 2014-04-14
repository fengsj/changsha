package com.newer.doudoule.auth;

/**
 * 定义微博授权时，所要参数
 * @author Administrator
 *
 */
public interface Constants {

	//当前应用的 APP_KEY
	public static final String APP_KEY = "2515417424";
	//授权回调页
	public static final String REDIRECT_URL = "http://www.sina.com";
	//Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
}
