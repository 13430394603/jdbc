package com.jdbc4.jdbc.sentence;

public class DeleteImpl extends SentenceImpl {

	public DeleteImpl() {}

	@Override
	public String head() {
		return "DELETE FROM " + tableName ;
	}

	@Override
	public String con() {
		return "" ;
	}

}
