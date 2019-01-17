package com.zhaohe.study.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class SuperStrategy {
	//只用于测试的字段，没有任何意义
	List<String> test=new ArrayList<String>();
	/**
	 * 判断json对象在传值过程中是否有某些属性，并将该属性返回
	 * @param json
	 * @param viewObj
	 * @param voAttr
	 * @return
	 * @throws Exception
	 */
	protected Object checkObject(String json,String viewObj,String voAttr) throws Exception{
		JSONObject obj=new JSONObject(json);
		if(obj.get(voAttr)==null){
			throw new Exception("前端页面的"+viewObj+"对象，在传值的过程中缺少"+voAttr+"字段");
		}
		return obj.get(voAttr);
	}
	public static void main(String[] args) {
		SuperStrategy s=new SuperStrategy();
		try {
			System.out.println(new SuperStrategy().checkObject(s,"test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 返回对象obj对应属性objAttr的值
	 * @param obj
	 * @param objAttr
	 * @return
	 * @throws Exception
	 */
	protected Object checkObject(Object obj,String objAttr) throws Exception{
		Class<?> classObj=obj.getClass();
		Object attrTemp=null;
		try {
			//获取objAttr对应对象的实例
			Field field=classObj.getDeclaredField(objAttr);
			field.setAccessible(true);
			//获取实例的值
			attrTemp=field.get(obj);
			if(attrTemp==null){
				throw new Exception("前端操作的"+classObj.getSimpleName()+"对象，在传值的过程中缺少"+objAttr+"字段");
			}
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return attrTemp;
	}
}
