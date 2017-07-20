package com.jacob.base.dao;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.jacob.base.bean.ClassSwitchTable;
import com.jacob.base.bean.DbTools;
import com.jacob.base.bean.PageVo;
import com.jacob.base.bean.PropertyColumn;
import com.jacob.base.bean.RowListMapper;
import com.jacob.base.bean.SqlPageQuery;
import com.jacob.base.bean.SqlQuery;
import com.jacob.base.bean.UpdatePreparedStatementCreator;

@SuppressWarnings({"rawtypes", "unchecked"})
public class BasicDao implements IBasicDao {
public static final Set<Class<?>> originClass = new HashSet<Class<?>>();
	static {
		originClass.add(Integer.class);
		originClass.add(Short.class);
		originClass.add(Byte.class);
		originClass.add(Float.class);
		originClass.add(Double.class);
		originClass.add(Long.class);
		originClass.add(Boolean.class);
		originClass.add(Character.class);
		originClass.add(String.class);
	}
	
	//注入方法1     
	@Resource private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    }

	protected static final Map<String, ClassSwitchTable> cstCache = new HashMap<String, 
		ClassSwitchTable>();
	
	
	
	@Override
	public void save(Object bean){
		
		Assert.notNull(bean);
		ClassSwitchTable ctt = cstCache.get(bean.getClass().getName());
		if(null==ctt){
			ctt = new ClassSwitchTable(bean.getClass());
			cstCache.put(bean.getClass().getName(), ctt);
		}
		UpdatePreparedStatementCreator preparedStatementCreator = new UpdatePreparedStatementCreator();
		preparedStatementCreator.insert(bean, ctt);
		try {
			jdbcTemplate.update(preparedStatementCreator);
		} catch (DataAccessException e) {
			System.out.println("保存数据报错:=================>>>"+e);
		}
		
	}
	
	@Override
	public int update(Object bean){
		Assert.notNull(bean);
		ClassSwitchTable ctt = cstCache.get(bean.getClass().getName());
		if(null==ctt){
			ctt = new ClassSwitchTable(bean.getClass());
			cstCache.put(bean.getClass().getName(), ctt);
		}

		UpdatePreparedStatementCreator preparedStatementCreator = new UpdatePreparedStatementCreator();
		preparedStatementCreator.update(bean, ctt);
		try {
			int i = jdbcTemplate.update(preparedStatementCreator);
			return i;
		} catch (DataAccessException e) {
			System.out.println("修改数据报错:=================>>>");
		} finally {
			
		}
		return -1;
	}
	
	@Override
	public int delete(Object bean){
		Assert.notNull(bean);
		ClassSwitchTable ctt = cstCache.get(bean.getClass().getName());
		if(null==ctt){
			ctt = new ClassSwitchTable(bean.getClass());
			cstCache.put(bean.getClass().getName(), ctt);
		}

		UpdatePreparedStatementCreator preparedStatementCreator = new UpdatePreparedStatementCreator();
		preparedStatementCreator.delete(bean, ctt);
		try {
			int i = jdbcTemplate.update(preparedStatementCreator);
			return i;
		} catch (DataAccessException e) {
			System.out.println("删除数据报错:=================>>>");
		}
		return -1;
	}
	
	@Override
	public void saveList(Collection<?> list, boolean autoCommit) {
		int index = 0;
		for(Object o : list) {
			index++;
			save(o);
			if(autoCommit && index==100){
				index = 0;
			}
		}
	}
	
	@Override
	public <E> E getEntityById(Class<E> cls, Serializable id, String... propertyNames) {
		
		ClassSwitchTable ctt=cstCache.get(cls.getName());
		if(null==ctt){
			ctt = new ClassSwitchTable(cls);
			cstCache.put(cls.getName(), ctt);
		}
		
		StringBuffer sql = new StringBuffer("");
		if(propertyNames==null || propertyNames.length==0){
			sql.append("SELECT * FROM "+ctt.getTableName()+" WHERE "+ctt.getIdName()+"=?");
		}else{
			Map<String, String> map = ctt.getClassTypeMap();
			sql.append("SELECT "+map.get(propertyNames[0]));
			for (int i = 1; i < propertyNames.length; i++) {
				sql.append(","+map.get(propertyNames[i]));
			}
			sql.append(" FROM "+ctt.getTableName()+" WHERE "+ctt.getIdName()+"=?");
		}
		List<E> list = jdbcTemplate.query(sql.toString(), new RowListMapper(ctt), id);
		
		if(list!=null)
			return list.get(0);
		return null;
	}

	@Override
	public <E> List<E> getListByProperty(Class<E> cls, String propName, Serializable propValue) {
		return getListByProperty(cls, propName, propValue, "*");
	}
	
	@Override
	public <E> List<E> getListByProperty(Class<E> cls, String propName, Serializable propValue, String... propertyNames) {
		ClassSwitchTable ctt=cstCache.get(cls.getName());
		if(null==ctt){
			ctt = new ClassSwitchTable(cls);
			cstCache.put(cls.getName(), ctt);
		}
		StringBuffer sql = new StringBuffer("");
		if(propertyNames==null || propertyNames.length==0 
				||(propertyNames.length==1 && propertyNames[0].equals("*"))){
			sql.append("SELECT * FROM "+ctt.getTableName()+" WHERE "+propName+" = ?");
		}else{
			Map<String, String> map = ctt.getClassTypeMap();
			sql.append("SELECT "+map.get(propertyNames[0]));
			for (int i = 1; i < propertyNames.length; i++) {
				sql.append(","+map.get(propertyNames[i]));
			}
			sql.append(" FROM "+ctt.getTableName()+" WHERE "+propName+"=?");
		}
		List<E> list = jdbcTemplate.query(sql.toString(), new RowListMapper(ctt), propValue);
		return list;
		
	}
	
	@Override
	public <E> E getOneByProperty(Class<E> cls, String propName, Serializable propValue) {
		return getListByProperty(cls, propName, propValue, "*").get(0);
	}

	@Override
	public List<Map<String, Object>> queryListMap(String sql, Object...params){
    	List<Map<String, Object>> r = jdbcTemplate.queryForList(sql, params);
        return r;
	}
	
	public <E> PageVo<E> queryPage(E cls, PageVo<E> oriPage) {
		if(null==cls){
			throw new NullPointerException("查询的对象为空  query obj is null");
		}
		ClassSwitchTable ctt=cstCache.get(cls.getClass().getName());
		if(null==ctt){
			ctt = new ClassSwitchTable(cls.getClass());
			cstCache.put(cls.getClass().getName(), ctt);
		}
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder ssql = new StringBuilder("SELECT * ");
		StringBuilder fsql = new StringBuilder(" FROM " + ctt.getTableName());
		StringBuilder wsql = new StringBuilder();
		
		PropertyColumn[] pcArr = ctt.getProColArr();
		if (ArrayUtils.isEmpty(pcArr)) {
			throw new RuntimeException(cls.getClass().getName() + " has not property");
		}
		
		int count = 0;
		for(PropertyColumn pc:pcArr){
			if(null!=pc){
				PropertyDescriptor pd = ctt.getPdByColumn(pc.getSqlColName());
				Object value = DbTools.callGetter(cls, pd);
				if(null!=value){
					if(count==0){
						wsql.append(" WHERE ");
					}else{
						wsql.append(" AND ");
					}
					wsql.append(pc.getSqlColName()).append(" =? ");
					params.add(value);
					count++;
				}
			}
		}
		
		SqlQuery sqlQuery = new SqlQuery();
		sqlQuery.setSelect(ssql.toString());
		sqlQuery.setFrom(fsql.toString());
		sqlQuery.setWhere(wsql.toString());
		return SqlPageQuery.query(this, cls.getClass(), sqlQuery, oriPage, params.toArray());
		
	}
	
	@Override
	public <E> E queryObject(Class<E> requiredType, String sql, Object... params) {
		E e = getJdbcTemplate().queryForObject(sql, requiredType, params);
		return e;
	}
	
	@Override
	public <E> List<E> query(Class<E> cls, String sql, Object... params) {
		if(null==cls){
			throw new NullPointerException("查询的对象为空  query obj is null");
		}
		if (originClass.contains(cls)) {
			return queryList(cls, sql, params);
		}
		ClassSwitchTable ctt = cstCache.get(cls.getName());
		if(null==ctt){
			ctt = new ClassSwitchTable(cls);
			cstCache.put(cls.getName(), ctt);
		}
        List<E> list = getJdbcTemplate().query(sql, new RowListMapper(ctt), params);
        return list;
	}
	
	@Override
	public <E> List<E> queryList(Class<E> cls, String sql, Object... params) {
		List<E> r = getJdbcTemplate().queryForList(sql, cls, params);
        return r;
	}
}
