package com.fom.context.helper;

/**
 * 
 * @author shanhm
 *
 * @param <V> 行数据解析结果类型
 */
public interface LocalZipParserHelper<V> extends ParserHelper<V> {

	/**
	 * 匹配zip的entry名称
	 * @param entryName entryName
	 * @return is matched 
	 */
	boolean matchEntryName(String entryName);
}
