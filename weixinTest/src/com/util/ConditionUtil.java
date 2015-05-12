package com.util;


import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


public class ConditionUtil {

	private Order order;
	private PagerModel pageModel;
	List<ConditionType> conditionTypes = new ArrayList<ConditionType>();

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public PagerModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PagerModel pageModel) {
		this.pageModel = pageModel;
	}

	public List<ConditionType> getConditionTypes() {
		return conditionTypes;
	}

	public void setConditionTypes(List<ConditionType> conditionTypes) {
		this.conditionTypes = conditionTypes;
	}

	private Criterion getCondition(ConditionType ct) {

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
						ct.getValue().toString(), MatchMode.START);
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

	public List<Criterion> criterion() {

		List<Criterion> criterions = new ArrayList<Criterion>();
		for (int i = 0; i < conditionTypes.size(); i++) {
			ConditionType ct = conditionTypes.get(i);
			if (ct.getLinkType() != ConditionType.LINK_OR) {

				criterions.add(getCondition(ct));
			} else {

				Criterion tCriterion = null;
				Disjunction dj = Restrictions.disjunction();
				List<ConditionType> ors = ct.getConditionTypes();
				for (int j = 0; j < ors.size(); j++) {
					tCriterion = dj.add(getCondition(ors.get(j)));
				}
				if (tCriterion != null) {
					criterions.add(tCriterion);
				}
			}
		}

		return criterions;
	}

	public ConditionUtil add(ConditionType ct) {
		this.conditionTypes.add(ct);
		return this;
	}

	public ConditionUtil addOrderUtil(Order order) {
		this.order = order;
		return this;
	}

	public ConditionUtil addFetch(int pageNo, int pageSize) {
		this.pageModel = new PagerModel(pageNo, pageSize);
		return this;
	}

}
