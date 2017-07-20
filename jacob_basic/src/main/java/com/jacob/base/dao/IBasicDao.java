package com.jacob.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IBasicDao {
	
	/**
	 * 保存数据
	 * @param bean 
	 * @return
	 */
	public void save(Object bean);
	
	/**
	 * 修改数据
	 * @param bean 
	 * @return
	 */
	public int update(Object bean);
	
	/**
	 * 删除数据
	 * @param bean 
	 * @return
	 */
	public int delete(Object bean);
	
	/**
	 * 保存集合到数据库
	 * @param list
	 * @param autoCommit 是否每保存100个对象就自动提交事务
	 */
	public void saveList(Collection<?> list, boolean autoCommit);
	
	/**
	 * 通过主键获取对象
	 * @param cls 
	 * @param id 
	 * @param propertyNames 需要查询的字段名
	 * @return
	 */
	public <E> E getEntityById(Class<E> cls, Serializable id, String... propertyNames);

	/**
	 * 根据对象属性查询单个对象
	 * @param cls 需要封装的对象Bean
	 * @param propName 属性名称
	 * @param propValue 属性值
	 * @return
	 */
	public <E> E getOneByProperty(Class<E> cls, String propName, Serializable propValue);
	
	/**
	 * 根据对象属性查询单个对象
	 * @param cls 需要封装的对象Bean
	 * @param propName 属性名称
	 * @param propValue 属性值
	 * @return
	 */
	public <E> List<E> getListByProperty(Class<E> cls, String propName, Serializable propValue);
	
	/**
	 * 根据对象属性查询对象指定属性的值
	 * @param cls 需要封装的对象Bean
	 * @param propName 属性名称
	 * @param propValue 属性值
	 * @return
	 */
	public <E> List<E> getListByProperty(Class<E> cls, String propName, Serializable propValue, String... propertyNames);
	
	/**
	 * 查询List 集合 返回键值对
	 * @param sql
	 * @param params
	 * @return
	 * @author hulb
	 * @date 2016年7月28日
	 */
	public List<Map<String, Object>> queryListMap(String sql, Object... params);
	
	/**
	 * 自定义SQL查询，返回定义的类对象集合
	 * @param cls 需要封装的对象Bean
	 * @param sql
	 * @param params
	 * @return
	 */
	public <E> List<E> query(Class<E> cls, String sql, Object... params);
	
	/**
	 * 原生查询，没有属性到字段的映射
	 * @param cls
	 * @param sql
	 * @param params
	 * @return
	 */
	public <E> List<E> queryList(Class<E> requiredType, String sql, Object... params);

	/**
	 * 自定义SQL查询，返回定义的类对象
	 * @param requiredType  Integer、Short、Byte、Float、Double、Long、Boolean、Character、String
	 * @param sql
	 * @param params
	 * @return
	 */
	public <E> E queryObject(Class<E> requiredType, String sql, Object... params) ;
}
