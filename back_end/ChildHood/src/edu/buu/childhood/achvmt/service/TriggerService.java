package edu.buu.childhood.achvmt.service;

public interface TriggerService {
	public void registerModule(EventSupport eventSupport);

	public boolean triggerEvent(EventSupport eventSupport);
	
	public boolean triggerEvent(EventSupport eventSupport,String arg);
	
}
