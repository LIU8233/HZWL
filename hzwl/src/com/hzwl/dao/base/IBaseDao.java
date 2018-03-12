package com.hzwl.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.hzwl.utils.PageBean;

/**
 * 抽取持久层通用方法
 * @author zhaoqx
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	//通用的修改方法
	public void executeUpdate(String queryName,Object ...objects);
	//通用的分页查询
	public void pageQuery(PageBean pageBean);
	//通用的查询，不分页
	public List<T> findByAnyQuery(DetachedCriteria d);
}
