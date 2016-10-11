package com.example.yuanmu.lunbo.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 怨幕
 * 该类里面的list和map里面存储的都是好友的信息，例如，拼音，帐号，头像，昵称，备注，个性签名�??
 *
 */
public class FriendData {
	public static String Status_IsChange = "isChange";
	public static String Status_NoChange = "nochange";
	public static String Status_Change = "friendlist_status";
	public static ArrayList<String> pinyinList = new ArrayList<String>();
	public static ArrayList<String> firstLetterList = new ArrayList<String>();
	public static Map<String, String> usernamemap = new HashMap<String, String>();
	public static Map<String, String> imgmap = new HashMap<String, String>();
	public static Map<String, String> remarkmap = new HashMap<String, String>();
}
