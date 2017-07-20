package com.jacob.base.bean;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.jacob.base.annoTable.GenerateKey;

/**
 * 更新记录：增加或者修改或删除
 * @author Administrator
 * 2016年12月23日16:06:36
 */
public class UpdatePreparedStatementCreator implements PreparedStatementCreator{
	protected static final Logger log = Logger.getLogger(UpdatePreparedStatementCreator.class);
	
	private String sql;
	private Object[] arrs;
	private String idName;
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getArrs() {
		return arrs;
	}

	public void setArrs(Object[] arrs) {
		this.arrs = arrs;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public UpdatePreparedStatementCreator() {
		super();
	}

	/**
	 * 更新数据库操作
	 * @param sql sql语句
	 * @param params 参数
	 */
	public UpdatePreparedStatementCreator(String sql, Object... params) {
		this(sql, null, params);
	}
	
	/**
	 * 更新数据库操作
	 * @param sql SQL语句
	 * @param idName 如果是insert into语句是否要取回主键,如果为空，则不能通过getIdValue()来获取主键
	 * @param params 参数
	 */
	public UpdatePreparedStatementCreator(String sql, String idName, Object... params){
		this.sql = sql;
		this.arrs = params;
		this.idName = idName;
	}
	
	@Override
	public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement psst = null;
		if(null==idName){
			psst = con.prepareStatement(sql);
		}else{
			psst = con.prepareStatement(sql, new String[]{idName});
		}
		
		if(null!=arrs){
			for(int i=0; i<arrs.length; i++){
				psst.setObject(i+1, arrs[i]);
			}
		}
		return psst;
	}

	/**
	 * 添加
	 * @param bean
	 * @param ctt
	 */
	@SuppressWarnings("rawtypes")
	public void insert(Object bean,ClassSwitchTable ctt){
		if(null==ctt){return;}
		StringBuffer sb = new StringBuffer("INSERT INTO ");
		StringBuilder values = new StringBuilder();
		List<Object> paramsList=new ArrayList<Object>();
		int count = 0;
		
		sb.append(ctt.getTableName()).append(" (");
		values.append(" VALUES (");
		for(PropertyColumn pcArr : ctt.getProColArr()){
			if(null!=pcArr){
				PropertyDescriptor pd = ctt.getPdByColumn(pcArr.getSqlColName());
				if(null==pd){
					log.warn(pcArr.getProColName()+"属性设置有误，请检查");
					continue;
				}
				Object value = DbTools.callGetter(bean, pd);
				if(null!=value){
					if(count>0){
						sb.append(", ");
						values.append(", ");
					}
					sb.append(pcArr.getSqlColName());
					values.append("? ");
					paramsList.add(value);
					count++;
				}else{
					if(pcArr.getSqlColName().equals(ctt.getIdName()) //是否为主键
							&& !ctt.getGenerateKey().equals(GenerateKey.IDENTITY)){//是否自增
						if(count>0){
							sb.append(", ");
							values.append(", ");
						}
						sb.append(pcArr.getSqlColName());
						values.append("? ");
						
						switch (ctt.getGenerateKey()) {//主键生成策略
							case UUID:
								String uuid = UUID.randomUUID().toString().replace("-", "");
								DbTools.callSetter(bean, pd, uuid);
								paramsList.add(uuid);
								break;
							default:
								break;
						}
						count++;
					}
				}
			}
		}
		values.append(")");
		sb.append(")");
		sb.append(values.toString());
		this.sql=sb.toString();
		this.arrs=paramsList.toArray();
		this.idName=ctt.getIdName();
		log.debug("sql语句："+this.sql+"===sql参数："+this.arrs+"===sql表名："+this.idName);
	}
	
	/**
	 * 修改
	 * @param bean
	 * @param ctt
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update(Object bean,ClassSwitchTable ctt){
		if(null==ctt){return;}
		Map<String, String> classTypeMap = ctt.getClassTypeMap();
		Object idValue = DbTools.callGetter(bean, 
				ctt.getPdByColumn(classTypeMap.get(ctt.getIdName())));
		if(null==idValue){
			throw new IllegalArgumentException("更新对象的主键值不能为空"+bean.toString());
		}
		
		StringBuffer sb = new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		PropertyColumn[] pcArr = ctt.getProColArr();
		int count = 0;

		sb.append("UPDATE ").append(ctt.getTableName()).append(" SET ");
		for(int i=0; i<pcArr.length; i++){
			if(null!=pcArr[i]){
				if(pcArr[i].getProColName().equals(ctt.getIdName())){
					continue;
				}
				PropertyDescriptor pd = ctt.getPdByColumn(pcArr[i].getSqlColName());
				Object value = DbTools.callGetter(bean, pd);
				if(null!=value){
					if(count>0){
						sb.append(", ");
					}
					sb.append(pcArr[i].getSqlColName()).append("=? ");
					params.add(value);
					count++;
				}
			}
		}
		
		sb.append("WHERE ").append(ctt.getClassTypeMap().get(ctt.getIdName())).append("=? ");
		params.add(idValue);
		this.sql = sb.toString();
		this.arrs = params.toArray(); 
		this.idName=ctt.getIdName();
		log.debug("sql语句："+this.sql);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void delete(Object bean,ClassSwitchTable ctt) {
		if(null==ctt){return;}
		Map<String, String> classTypeMap = ctt.getClassTypeMap();
		Object idValue = DbTools.callGetter(bean, ctt.getPdByColumn(classTypeMap.get(ctt.getIdName())));
		if(null==idValue){
			throw new IllegalArgumentException("删除信息的对象，主键值不能为空"+bean.toString());
		}
		List<Object> params=new ArrayList<Object>();
		params.add(idValue);
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ").append(ctt.getTableName());
		sb.append("WHERE ").append(ctt.getClassTypeMap().get(ctt.getIdName())).append("=? ");
		this.sql = sb.toString();
		this.arrs = params.toArray();
		this.idName = ctt.getIdName();
		log.debug("sql语句："+this.sql);
	}
}
