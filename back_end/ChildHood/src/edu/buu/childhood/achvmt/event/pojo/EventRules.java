package edu.buu.childhood.achvmt.event.pojo;

/*
 * 2016/9/27
 * @Author Joe
 * 
 * 映射T_ACHVMT_EVENT_RULES表
 */
public class EventRules {
	private int ruleId;
	private String ruleName;
	private String ruleSynopsis;
	private int ruleModule;
	private String ruleModuleName;
	private String ruleProcedure;
	private char procedureHasarg;
	private String note;
	private char enable;

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleSynopsis() {
		return ruleSynopsis;
	}

	public void setRuleSynopsis(String ruleSynopsis) {
		this.ruleSynopsis = ruleSynopsis;
	}

	public int getRuleModule() {
		return ruleModule;
	}

	public void setRuleModule(int ruleModule) {
		this.ruleModule = ruleModule;
	}

	public String getRuleModuleName() {
		return ruleModuleName;
	}

	public void setRuleModuleName(String ruleModuleName) {
		this.ruleModuleName = ruleModuleName;
	}

	public String getRuleProcedure() {
		return ruleProcedure;
	}

	public void setRuleProcedure(String ruleProcedure) {
		this.ruleProcedure = ruleProcedure;
	}

	public char getProcedureHasarg() {
		return procedureHasarg;
	}

	public void setProcedureHasarg(char procedureHasarg) {
		this.procedureHasarg = procedureHasarg;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public char getEnable() {
		return enable;
	}

	public void setEnable(char enable) {
		this.enable = enable;
	}

}
