package com.zhaohe.study.sql.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.google.gson.JsonElement;

public class StringUtil {
	public static <T> String join(T[] objs, String targetStr,String separator, Object obj,
			Param param) {
		if (objs == null) {
			return null;
		}
		int endIndex = objs.length;
		int startIndex = 0;
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return "";
		}
		bufSize *= ((objs[startIndex] == null ? 16 : objs[startIndex]
				.toString().length()) + 1);
		StrBuilder buf = new StrBuilder(bufSize);
		param.setArgs(new ArrayList<>(endIndex - 1));
		param.setKey(new StringBuilder(endIndex - 1));
		int counter = 0;
		for (int i = startIndex; i < endIndex; i++) {
			counter = convertType(objs[i], buf, counter,targetStr, separator, obj, param);
		}
		return buf.toString();
	}

	/**
	 * 
	 * @Description: TODO(这的代码得优化)
	 * @return void
	 */
	private static <T> int convertType(T tmp, StrBuilder buf, int counter,
			String targetStr,String separator, Object obj, Param param) {
			if (tmp != null) {
				if (Field.class == tmp.getClass()) {
					return fieldOperateConcatSql(tmp, obj, counter, buf, separator, targetStr, param);
				}
				//这块代码不好，目前是写死的父类接口
				if(Entry.class == tmp.getClass().getInterfaces()[0]){
					return entryOperateConcatSql(tmp, counter, buf, separator, targetStr, param);
				}
			}
		return counter;
	}
	@SuppressWarnings("unchecked")
	private static <T> int entryOperateConcatSql(T tmp,int counter,StrBuilder buf,String separator,String targetStr,Param param){
		Entry<String,JsonElement> entry = (Entry<String,JsonElement>) tmp;
		if(counter>0){
			buf.append(separator);
			param.getKey().append(separator);
		}
		if(entry!=null){
			buf.append(entry.getKey()+targetStr);
			param.getArgs().add(entry.getValue().getAsString());
		}
		param.getKey().append("?");
		counter++;
		return counter;
	}
	private static <T> int fieldOperateConcatSql(T tmp,Object obj,int counter,StrBuilder buf,String separator,String targetStr,Param param){
		Field field = (Field) tmp;
		field.setAccessible(true);
		int modifier = field.getModifiers();
		try{
			Object o = field.get(obj);
			if ((Modifier.PUBLIC == modifier || Modifier.PRIVATE == modifier)
					&& o != null) {
				if (counter != 0) {
					buf.append(separator);
					param.getKey().append(separator);
				}
				counter++;
				param.getArgs().add(o);
				buf.append(field.getName());
				if(StringUtils.isNotBlank(targetStr))
					buf.append(targetStr);
				param.getKey().append("?");
			}
		}catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return counter;
	}
}
