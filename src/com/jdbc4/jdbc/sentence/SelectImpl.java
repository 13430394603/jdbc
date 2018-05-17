package com.jdbc4.jdbc.sentence;

public class SelectImpl extends SentenceImpl {
	
	public SelectImpl() {}

	@Override
	public String head() {
		return "SELECT" ;
	}

	@Override
	public String con() {
		return " * FROM " + tableName ;
	}

}
