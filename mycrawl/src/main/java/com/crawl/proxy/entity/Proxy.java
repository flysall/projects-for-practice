package com.crawl.proxy.entity;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Proxy implements Delayed, Serializable{
	private static final long seriaVersionUID = -7583883432417635332L;
	//任务间隔时间
	private long timeInterval;
	private String ip;
	private int port;
	private boolean availableFlag;
	private boolean anonymousFlag;
	//最近一次请求成功时间
	private long lastSuccessfulTime;
	private long successfulTotalTime; //请求成功总耗时
	private int failureTimes;	//请求失败次数
	private int successfulTimes;//请求成功次数
	private double successfulAverageTime;//请求成功平均耗时
	public Proxy(String ip, int port, long timeInterval){
		this.ip = ip;
		this.port = port;
		this.timeInterval = timeInterval;
		this.timeInterval = TimeUnit.NANOSECONDS.convert(timeInterval, TimeUnit.MILLISECONDS) + System.nanoTime();
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isAvailableFlag() {
        return availableFlag;
    }

    public void setAvailableFlag(boolean availableFlag) {
        this.availableFlag = availableFlag;
    }

    public boolean isAnonymousFlag() {
        return anonymousFlag;
    }

    public void setAnonymousFlag(boolean anonymousFlag) {
        this.anonymousFlag = anonymousFlag;
    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public long getLastSuccessfulTime() {
        return lastSuccessfulTime;
    }
    
    public void setLastSuccessfulTime(long lastSuccessfulTime) {
        this.lastSuccessfulTime = lastSuccessfulTime;
    }

    public long getSuccessfulTotalTime() {
        return successfulTotalTime;
    }

    public void setSuccessfulTotalTime(long successfulTotalTime) {
        this.successfulTotalTime = successfulTotalTime;
    }

    public void setTimeInterval(long timeInterval){
        this.timeInterval=TimeUnit.NANOSECONDS.convert(timeInterval, TimeUnit.MILLISECONDS) + System.nanoTime();
    }
    
    public long getDelay(TimeUnit unit){
    	return unit.convert(timeInterval - System.nanoTime(), TimeUnit.NANOSECONDS);
    }
    
    public int compareTo(Delayed o){
    	Proxy element = (Proxy)o;
    	if (successfulAverageTime == 0.0d || element.successfulAverageTime == 0.0d){
    		return 0;
    	}
    	return successfulAverageTime > element.successfulAverageTime ? 1 : (successfulAverageTime < element.successfulAverageTime ? -1 : 0); 
    }
    
    public int getFailureTimes() {
        return failureTimes;
    }

    public void setFailureTimes(int failureTimes) {
        this.failureTimes = failureTimes;
    }

    public int getSuccessfulTimes() {
        return successfulTimes;
    }

    public void setSuccessfulTimes(int successfulTimes) {
        this.successfulTimes = successfulTimes;
    }

    public double getSuccessfulAverageTime() {
        return successfulAverageTime;
    }

    public void setSuccessfulAverageTime(double successfulAverageTime) {
        this.successfulAverageTime = successfulAverageTime;
    }
    
}
