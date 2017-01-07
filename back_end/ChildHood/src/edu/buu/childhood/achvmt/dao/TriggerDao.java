package edu.buu.childhood.achvmt.dao;

public interface TriggerDao {
	public boolean callDispatcher(String moduleName);

	public boolean callDispatcher(String moduleName, String arg);
}
