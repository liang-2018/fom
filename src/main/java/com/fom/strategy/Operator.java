package com.fom.strategy;

import java.io.File;
import java.io.InputStream;

/**
 * 
 * @author shanhm1991
 * @date 2019年1月21日
 *
 */
public interface Operator {

	/**
	 * 根据url打开文件输入流
	 * @param url
	 * @return
	 * @throws Exception
	 */
	InputStream open(String url) throws Exception;
	
	/**
	 * 根据url下载文件
	 * @param url
	 * @param file
	 * @throws Exception
	 */
	void download(String url, File file) throws Exception;
	
	/**
	 * 根据url删除文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	boolean delete(String url) throws Exception;
}
