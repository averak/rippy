package dev.abelab.rippy.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDateExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table event_date
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table event_date
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table event_date
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public EventDateExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table event_date
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andEventIdIsNull() {
            addCriterion("event_id is null");
            return (Criteria) this;
        }

        public Criteria andEventIdIsNotNull() {
            addCriterion("event_id is not null");
            return (Criteria) this;
        }

        public Criteria andEventIdEqualTo(Integer value) {
            addCriterion("event_id =", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotEqualTo(Integer value) {
            addCriterion("event_id <>", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdGreaterThan(Integer value) {
            addCriterion("event_id >", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("event_id >=", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdLessThan(Integer value) {
            addCriterion("event_id <", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdLessThanOrEqualTo(Integer value) {
            addCriterion("event_id <=", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdIn(List<Integer> values) {
            addCriterion("event_id in", values, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotIn(List<Integer> values) {
            addCriterion("event_id not in", values, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdBetween(Integer value1, Integer value2) {
            addCriterion("event_id between", value1, value2, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotBetween(Integer value1, Integer value2) {
            addCriterion("event_id not between", value1, value2, "eventId");
            return (Criteria) this;
        }

        public Criteria andDateOrderIsNull() {
            addCriterion("date_order is null");
            return (Criteria) this;
        }

        public Criteria andDateOrderIsNotNull() {
            addCriterion("date_order is not null");
            return (Criteria) this;
        }

        public Criteria andDateOrderEqualTo(Integer value) {
            addCriterion("date_order =", value, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderNotEqualTo(Integer value) {
            addCriterion("date_order <>", value, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderGreaterThan(Integer value) {
            addCriterion("date_order >", value, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("date_order >=", value, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderLessThan(Integer value) {
            addCriterion("date_order <", value, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderLessThanOrEqualTo(Integer value) {
            addCriterion("date_order <=", value, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderIn(List<Integer> values) {
            addCriterion("date_order in", values, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderNotIn(List<Integer> values) {
            addCriterion("date_order not in", values, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderBetween(Integer value1, Integer value2) {
            addCriterion("date_order between", value1, value2, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andDateOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("date_order not between", value1, value2, "dateOrder");
            return (Criteria) this;
        }

        public Criteria andStartAtIsNull() {
            addCriterion("start_at is null");
            return (Criteria) this;
        }

        public Criteria andStartAtIsNotNull() {
            addCriterion("start_at is not null");
            return (Criteria) this;
        }

        public Criteria andStartAtEqualTo(Date value) {
            addCriterion("start_at =", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtNotEqualTo(Date value) {
            addCriterion("start_at <>", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtGreaterThan(Date value) {
            addCriterion("start_at >", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtGreaterThanOrEqualTo(Date value) {
            addCriterion("start_at >=", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtLessThan(Date value) {
            addCriterion("start_at <", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtLessThanOrEqualTo(Date value) {
            addCriterion("start_at <=", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtIn(List<Date> values) {
            addCriterion("start_at in", values, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtNotIn(List<Date> values) {
            addCriterion("start_at not in", values, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtBetween(Date value1, Date value2) {
            addCriterion("start_at between", value1, value2, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtNotBetween(Date value1, Date value2) {
            addCriterion("start_at not between", value1, value2, "startAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtIsNull() {
            addCriterion("finish_at is null");
            return (Criteria) this;
        }

        public Criteria andFinishAtIsNotNull() {
            addCriterion("finish_at is not null");
            return (Criteria) this;
        }

        public Criteria andFinishAtEqualTo(Date value) {
            addCriterion("finish_at =", value, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtNotEqualTo(Date value) {
            addCriterion("finish_at <>", value, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtGreaterThan(Date value) {
            addCriterion("finish_at >", value, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_at >=", value, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtLessThan(Date value) {
            addCriterion("finish_at <", value, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtLessThanOrEqualTo(Date value) {
            addCriterion("finish_at <=", value, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtIn(List<Date> values) {
            addCriterion("finish_at in", values, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtNotIn(List<Date> values) {
            addCriterion("finish_at not in", values, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtBetween(Date value1, Date value2) {
            addCriterion("finish_at between", value1, value2, "finishAt");
            return (Criteria) this;
        }

        public Criteria andFinishAtNotBetween(Date value1, Date value2) {
            addCriterion("finish_at not between", value1, value2, "finishAt");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table event_date
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table event_date
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}