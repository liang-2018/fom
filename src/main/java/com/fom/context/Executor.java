package com.fom.context;

import java.io.File;

import org.apache.log4j.Logger;

import com.fom.util.exception.WarnException;
import com.fom.util.log.LoggerFactory;

/**
 * 
 * @author shanhm1991
 *
 * @param <E>
 */
public abstract class Executor<E extends Config> extends Thread {

	E config;

	protected final Logger log;

	protected final String name;

	protected final String path;

	protected final File srcFile;

	protected final long srcSize;

	protected final String srcName;

	protected Executor(String name, String path) { 
		this.name = name;
		this.path = path;
		this.srcFile = new File(path);
		this.srcName = srcFile.getName();
		this.srcSize = srcFile.length() / 1024;
		Config config = getRuntimeConfig();
		if(config == null){
			throw new RuntimeException("config获取失败");
		}
		this.setName(config.getType() + "[" + srcName + "]");
		this.log = LoggerFactory.getLogger(config.getType() + "." + name);
	}

	/**
	 * 获取最新的config
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected final E getRuntimeConfig(){
		return (E)ConfigManager.getConfig(name);
	}

	@Override
	public final void run(){
		config = getRuntimeConfig();
		if(config == null){
			log.info("配置已卸载，任务中止."); 
			return;
		}
		Thread.currentThread().setName(config.getType() + "[" + srcName + "]");

		long runTime = System.currentTimeMillis();
		try {
			executeBefore(config);
			execute();
			log.info(config.getTypeName() + "任务结束, 耗时=" + (System.currentTimeMillis() - runTime) + "ms");
		} catch(WarnException e){
			log.warn(config.getTypeName() + "任务错误结束[" + e.getMessage() + "], 耗时=" + (System.currentTimeMillis() - runTime + "ms"));
		} catch (InterruptedException e) {
			//设置检测点:每次batchProcessLineData之前
			log.warn(config.getTypeName() + "任务中断[" + e.getMessage() + "], 耗时=" + (System.currentTimeMillis() - runTime + "ms"));
		} catch(Throwable e) {
			log.error(config.getTypeName() + "任务异常结束, 耗时=" + (System.currentTimeMillis() - runTime + "ms"), e);
		} finally{
			executeAfter();
		}
	}

	protected void executeBefore(E config) throws Exception {

	}

	abstract void execute() throws Exception;

	void executeAfter() {

	}

}
