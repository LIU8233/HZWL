package com.hzwl.service;

import java.util.List;

import com.hzwl.entity.Suggestion;

public interface ISuggestionService {
	
	public void addSuggestion(Suggestion entity);
	
	public List<Suggestion> findAll();

	public void delete(int id);

	public void updata(int id);

}
