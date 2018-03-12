package com.hzwl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.ISuggestionDao;
import com.hzwl.entity.Suggestion;
import com.hzwl.service.ISuggestionService;


@Service
@Transactional
public class SuggestionServiceImpl implements ISuggestionService{

	@Autowired
	private ISuggestionDao suggestionDao;
	
	@Override
	public void addSuggestion(Suggestion entity) {
		// TODO Auto-generated method stub
		suggestionDao.save(entity);
		
	}

	@Override
	public List<Suggestion> findAll() {
		// TODO Auto-generated method stub
		return suggestionDao.findAll();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		suggestionDao.executeUpdate("suggestion.delete", id);
	}

	@Override
	public void updata(int id) {
		// TODO Auto-generated method stub
		suggestionDao.executeUpdate("suggestion.updata", id);
	}

}
