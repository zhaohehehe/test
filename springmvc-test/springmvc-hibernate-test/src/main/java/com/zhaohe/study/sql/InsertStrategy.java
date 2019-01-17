package com.zhaohe.study.sql;

import java.lang.reflect.Field;
public class InsertStrategy extends SuperStrategy implements StrategyAbstract{
	
	private <T> String initAssembly(Class<T> classT){
		return "insert into "+classT.getSimpleName().toLowerCase()+" ";
	}
	@Override
	public <T> Object executeStrategy(Class<T> classt, String json) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> Object executeStrategy(Object obj) {
		Class<?> classT=obj.getClass();
		Field[] fields=classT.getDeclaredFields();
		Param param; 
		return null;
	}
}
