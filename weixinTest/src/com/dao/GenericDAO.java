package com.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import com.util.ConditionType;
import com.util.PagerModel;


/**
 * 
 * @author haoren
 * 
 * @param <T>
 */
public interface GenericDAO<T> extends Serializable {

	public Criteria createCriteria();

	public Criteria createCriteria(Criterion... criterions);

	public Criteria createCriteria(List<ConditionType> conditionTypes);

	public int count();

	public int count(ConditionType conditionType);

	public int count(List<ConditionType> conditionTypes);

	public T find(Serializable id);

	public T findUniqueByProperty(String[] propertyName, Object[] value);

	public List<T> find(ConditionType conditionType);

	public List<T> find(List<ConditionType> conditionTypes);

	public List<T> find(List<ConditionType> conditionTypes, List<Order> orders);

	public T findUniqueByProperty(String propertyName, Object value);

	public void merge(Object entity);

	public void modify(Object entity);

	public Serializable save(Object entity);

	public void saveOrUpdate(Object entity);

	public void remove(Object entity);

	public List<T> findAll();

	public void removeAll();

	public PagerModel findByPager(int offset, int pageSize);

	public PagerModel findByPager(ConditionType conditionType, int offset,
			int pageSize);

	public PagerModel findByPager(List<ConditionType> conditionTypes,
			int offset, int pageSize);

	public PagerModel findByPager(List<ConditionType> conditionTypes,
			List<Order> orders, int offset, int pageSize);

}
