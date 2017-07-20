package com.jacob.base.bean;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RowListMapper<E> implements RowMapper<E>{
	
	private Class<E> cls;
	private ClassSwitchTable<E> ctt;
	
	public RowListMapper(Class<E> cls) {
		this.cls = cls;
		ctt = new ClassSwitchTable<E>(cls);
	}
	public RowListMapper(ClassSwitchTable<E> ctt) {
		this.ctt = ctt;
		this.cls = ctt.getCls();
	}
	
	public E mapRow(ResultSet rs, int arg1) throws SQLException {
		System.out.println("ResultSet:"+rs.toString());
		E bean;
		try {
			bean = this.cls.newInstance();
		} catch (InstantiationException e) {
			throw new SQLException(
	                "Cannot create " + this.cls.getName() + ": " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new SQLException(
	                "Cannot create " + this.cls.getName() + ": " + e.getMessage());
		}
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int col = 1; col <= cols; col++) {
            String columnName = rsmd.getColumnLabel(col).toLowerCase();
            if (null == columnName || 0 == columnName.length()) {
                columnName = rsmd.getColumnName(col).toLowerCase();
            }
            PropertyDescriptor pd = ctt.getPdByColumn(columnName);
            
            if(null!=pd){
            	Class<?> propType = pd.getPropertyType();
            	
                Object value = DbTools.processColumn(rs, columnName, propType);
                if (propType != null && value == null && propType.isPrimitive()) {
                    value = DbTools.primitiveDefaults.get(propType);
                }
                DbTools.callSetter(bean, pd, value);
            }
		}
        return bean;
	}
}
