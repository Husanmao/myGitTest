package com.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.dao.GenericDAO;
import com.util.ConditionType;
import com.util.PagerModel;

/**
 * 
 * @author haoren
 * 
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class GenericDAOImpl<T> extends HibernateDaoSupport implements
		GenericDAO<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2638934836888612350L;
	protected Class<T> entityClass;

	public GenericDAOImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Class<T> getEntityClass() {
		return this.entityClass;
	}

	public Criteria createCriteria() {
		return getSession().createCriteria(getEntityClass());
	}

	public Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	protected Criterion getCondition(ConditionType ct) {

		if (ct != null) {
			int oper = ct.getIoperator();
			if (ConditionType.IOPER_EQ == oper) {
				return Restrictions.eq(ct.getName(), ct.getValue());
			} else if (ConditionType.IOPER_NE == oper) {
				return Restrictions.ne(ct.getName(), ct.getValue());
			} else if (ConditionType.IOPER_GE == oper) {
				return Restrictions.ge(ct.getName(), ct.getValue());
			} else if (ConditionType.IOPER_GT == oper) {
				return Restrictions.gt(ct.getName(), ct.getValue());
			} else if (ConditionType.IOPER_LE == oper) {
				return Restrictions.le(ct.getName(), ct.getValue());
			} else if (ConditionType.IOPER_LT == oper) {
				return Restrictions.lt(ct.getName(), ct.getValue());
			} else if (ConditionType.IOPER_NULL == oper) {
				return Restrictions.isNull(ct.getName());
			} else if (ConditionType.IOPER_NONULL == oper) {
				return Restrictions.isNotNull(ct.getName());
			} else if (ConditionType.IOPER_LIKE == oper) {
				return Restrictions.like(ct.getName(),
						ct.getValue().toString(), MatchMode.ANYWHERE);
			} else if (ConditionType.IOPER_IN == oper) {
				return Restrictions.in(ct.getName(), ct.getValues());
			} else if (ConditionType.IOPER_EQProperty == oper) {
				return Restrictions.eqProperty(ct.getName(), ct.getOtherName());
			} else if (ConditionType.IOPER_LEProperty == oper) {
				return Restrictions.leProperty(ct.getName(), ct.getOtherName());
			} else if (ConditionType.IOPER_GEProperty == oper) {
				return Restrictions.geProperty(ct.getName(), ct.getOtherName());
			} else if (ConditionType.IOPER_GTProperty == oper) {
				return Restrictions.gtProperty(ct.getName(), ct.getOtherName());
			} else if (ConditionType.IOPER_LTProperty == oper) {
				return Restrictions.ltProperty(ct.getName(), ct.getOtherName());
			} else if (ConditionType.IOPER_NEProperty == oper) {
				return Restrictions.neProperty(ct.getName(), ct.getOtherName());
			}
		}
		return null;
	}

	public Criteria createCriteria(List<ConditionType> conditionTypes) {
		if (conditionTypes == null) {
			return this.createCriteria();
		} else {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			for (int i = 0; i < conditionTypes.size(); i++) {
				ConditionType ct = conditionTypes.get(i);
				if (ct.getLinkType() != ConditionType.LINK_OR) {
					criteria.add(getCondition(ct));
				} else {
					Criterion tCriterion = null;
					Disjunction dj = Restrictions.disjunction();
					List<ConditionType> cts = ct.getConditionTypes();
					for (int j = 0; j < cts.size(); j++) {
						tCriterion = dj.add(getCondition(cts.get(j)));
					}
					if (tCriterion != null) {
						criteria.add(tCriterion);
					}
				}
			}
			return criteria;
		}
	}

	protected int countByCriteria(Criteria criteria) {
		if (criteria == null) {
			return this.count();
		} else {
			return ((Number) (criteria.setProjection(Projections.rowCount())
					.uniqueResult())).intValue();
		}
	}

	protected List<T> findByCriteria(Criteria criteria) {
		return criteria.list();
	}

	public int count() {
		Criteria criteria = createCriteria();
		return countByCriteria(criteria);
	}

	public int count(ConditionType conditionType) {
		List<ConditionType> conditionTypes = new ArrayList<ConditionType>();
		conditionTypes.add(conditionType);
		Criteria criteria = createCriteria(conditionTypes);
		return countByCriteria(criteria);
	}

	public int count(List<ConditionType> conditionTypes) {
		Criteria criteria = createCriteria(conditionTypes);
		return countByCriteria(criteria);
	}

	public T find(Serializable id) {
		return (T) getHibernateTemplate().get(getEntityClass(), id);
	}

	public List<T> find(ConditionType conditionType) {
		if (conditionType == null) {
			return this.findAll();
		} else {
			List<ConditionType> conditionTypes = new ArrayList<ConditionType>();
			conditionTypes.add(conditionType);
			Criteria criteria = createCriteria(conditionTypes);
			return criteria.list();
		}
	}

	public List<T> find(List<ConditionType> conditionTypes) {
		if (conditionTypes == null) {
			return this.findAll();
		} else {
			Criteria criteria = createCriteria(conditionTypes);
			return criteria.list();
		}
	}

	public List<T> find(List<ConditionType> conditionTypes, List<Order> orders) {

		if (orders == null) {
			return this.find(conditionTypes);
		} else {

			Criteria criteria = createCriteria(conditionTypes);

			for (int i = 0; i < orders.size(); i++) {
				criteria.addOrder(orders.get(i));
			}
			return criteria.list();
		}
	}

	public T findUniqueByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return (T) createCriteria(Restrictions.eq(property, value))
				.uniqueResult();
	}

	public T findUniqueByProperty(String[] property, Object[] value) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		for (int i = 0; i < property.length; i++) {
			criteria.add(Restrictions.eq(property[i], value[i]));
		}
		return (T) criteria.uniqueResult();
	}

	public void merge(Object entity) {
		getHibernateTemplate().merge(entity);
	}

	public void modify(Object entity) {
		getHibernateTemplate().update(entity);
	}

	public Serializable save(Object entity) {
		return getHibernateTemplate().save(entity);
	}

	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void remove(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public List<T> findAll() {
		return getHibernateTemplate().find(
				"from " + entityClass.getSimpleName());
	}

	public void removeAll() {
		List<T> list = (List<T>) getHibernateTemplate().loadAll(entityClass);
		getHibernateTemplate().deleteAll(list);
	}

	public PagerModel findByPager(int offset, int pageSize) {

		Criteria criteria = getSession().createCriteria(getEntityClass());
		int total = this.countByCriteria(criteria);
		criteria.setProjection(null);

		criteria.setFirstResult(offset);
		criteria.setMaxResults(pageSize);
		List<T> list = criteria.list();
		PagerModel pm = new PagerModel(offset, pageSize);
		pm.setTotal(total);
		pm.setList(list);
		return pm;
	}

	public PagerModel findByPager(ConditionType conditionType, int offset,
			int pageSize) {

		if (conditionType == null) {
			return this.findByPager(offset, pageSize);
		} else {
			List<ConditionType> conditionTypes = new ArrayList<ConditionType>();
			conditionTypes.add(conditionType);
			Criteria criteria = createCriteria(conditionTypes);

			int total = this.countByCriteria(criteria);

			criteria.setProjection(null);
			criteria.setFirstResult(offset);
			criteria.setMaxResults(pageSize);

			List<T> list = criteria.list();

			PagerModel pm = new PagerModel(offset, pageSize);
			pm.setTotal(total);
			pm.setList(list);
			return pm;
		}
	}

	public PagerModel findByPager(List<ConditionType> conditionTypes,
			int offset, int pageSize) {

		if (conditionTypes == null) {
			return this.findByPager(offset, pageSize);
		} else {

			Criteria criteria = createCriteria(conditionTypes);

			int total = this.countByCriteria(criteria);

			criteria.setProjection(null);
			criteria.setFirstResult(offset);
			criteria.setMaxResults(pageSize);

			List<T> list = criteria.list();

			PagerModel pm = new PagerModel(offset, pageSize);
			pm.setTotal(total);
			pm.setList(list);
			return pm;
		}
	}

	public PagerModel findByPager(List<ConditionType> conditionTypes,
			List<Order> orders, int offset, int pageSize) {

		if (orders == null) {
			return this.findByPager(conditionTypes, offset, pageSize);
		} else {

			Criteria criteria = createCriteria(conditionTypes);

			int total = this.countByCriteria(criteria);

			criteria.setProjection(null);
			for (int i = 0; i < orders.size(); i++) {
				criteria.addOrder(orders.get(i));
			}
			criteria.setFirstResult(offset);
			criteria.setMaxResults(pageSize);

			List<T> list = criteria.list();

			PagerModel pm = new PagerModel(offset, pageSize);
			pm.setPagesize(pageSize);
			pm.setTotal(total);
			pm.setList(list);
			return pm;
		}
	}

}