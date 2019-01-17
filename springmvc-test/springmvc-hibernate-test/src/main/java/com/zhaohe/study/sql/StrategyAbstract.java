package com.zhaohe.study.sql;

public interface StrategyAbstract {
	public <T> Object executeStrategy(Class<T> classt,String json);
	public <T> Object executeStrategy(Object obj);
}
