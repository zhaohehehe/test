package com.zhaohe.study.sql;

import java.util.List;

public class Param {
	private StringBuilder key;
	private List<Object> args;
	/**
	 * @return the key
	 */
	public StringBuilder getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(StringBuilder key) {
		this.key = key;
	}
	/**
	 * @return the args
	 */
	public List<Object> getArgs() {
		return args;
	}
	/**
	 * @param args the args to set
	 */
	public void setArgs(List<Object> args) {
		this.args = args;
	}
	
}
