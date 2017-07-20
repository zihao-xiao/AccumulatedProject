package com.jacob.base.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 数据库操作常用功能封装，需要依赖apache commons DbUtils
 * @author hulb
 *
 */
public class DbTools {
	private static final Logger logger = Logger.getLogger(DbTools.class);
	protected static final String WHERE = " WHERE ";
	protected static final String EQUALS = "=?";
	protected static final String QUOT = "'";
	public static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>();
	
	static {
        primitiveDefaults.put(Integer.TYPE, Integer.valueOf(0));
        primitiveDefaults.put(Short.TYPE, Short.valueOf((short) 0));
        primitiveDefaults.put(Byte.TYPE, Byte.valueOf((byte) 0));
        primitiveDefaults.put(Float.TYPE, Float.valueOf(0f));
        primitiveDefaults.put(Double.TYPE, Double.valueOf(0d));
        primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
        primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
        primitiveDefaults.put(Character.TYPE, Character.valueOf((char) 0));
    };
    
	/**
	 * 根据类型获取字段的值，返回相应的类型值
	 * @param rs
	 * @param colName 字段名
	 * @param propType 字段的类型
	 * @return
	 * @throws SQLException
	 */
	public static Object processColumn(ResultSet rs, String colName, Class<?> propType)
			throws SQLException {
		if (!propType.isPrimitive() && rs.getObject(colName) == null) {
			return null;
		}
		if (propType.equals(String.class)) {
			return rs.getString(colName);
		} else if (propType.equals(Integer.TYPE)
				|| propType.equals(Integer.class)) {
			return Integer.valueOf(rs.getInt(colName));
		} else if (propType.equals(Boolean.TYPE)
				|| propType.equals(Boolean.class)) {
			return Boolean.valueOf(rs.getBoolean(colName));
		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			return Long.valueOf(rs.getLong(colName));
		} else if (propType.equals(Double.TYPE)
				|| propType.equals(Double.class)) {
			return Double.valueOf(rs.getDouble(colName));
		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return Float.valueOf(rs.getFloat(colName));
		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return Short.valueOf(rs.getShort(colName));
		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return Byte.valueOf(rs.getByte(colName));
		} else if (propType.equals(Timestamp.class)) {
			return rs.getTimestamp(colName);
		} else if (propType.equals(SQLXML.class)) {
			return rs.getSQLXML(colName);
		} else {
			return rs.getObject(colName);
		}

	}
	
	/**
	 * 根据类型获取字段的值，返回相应的类型值
	 * @param rs
	 * @param colName 字段名
	 * @param propType 字段的类型
	 * @return
	 * @throws SQLException
	 */
	public static Object processColumn(ResultSet rs, int colIndex, Class<?> propType)
			throws SQLException {
		if (!propType.isPrimitive() && rs.getObject(colIndex) == null) {
			return null;
		}
		if (propType.equals(String.class)) {
			return rs.getString(colIndex);
		} else if (propType.equals(Integer.TYPE)
				|| propType.equals(Integer.class)) {
			return Integer.valueOf(rs.getInt(colIndex));
		} else if (propType.equals(Boolean.TYPE)
				|| propType.equals(Boolean.class)) {
			return Boolean.valueOf(rs.getBoolean(colIndex));
		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			return Long.valueOf(rs.getLong(colIndex));
		} else if (propType.equals(Double.TYPE)
				|| propType.equals(Double.class)) {
			return Double.valueOf(rs.getDouble(colIndex));
		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return Float.valueOf(rs.getFloat(colIndex));
		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return Short.valueOf(rs.getShort(colIndex));
		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return Byte.valueOf(rs.getByte(colIndex));
		} else if (propType.equals(Timestamp.class)) {
			return rs.getTimestamp(colIndex);
		} else if (propType.equals(SQLXML.class)) {
			return rs.getSQLXML(colIndex);
		} else {
			return rs.getObject(colIndex);
		}

	}

	public static <E>  E createBean(ResultSet rs, ClassSwitchTable<E> ctt, Class<E> eClas)
            throws SQLException {
        E bean;
		try {
			bean = eClas.newInstance();
		} catch (InstantiationException e) {
			throw new SQLException(
	                "Cannot create " + eClas.getName() + ": " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new SQLException(
	                "Cannot create " + eClas.getName() + ": " + e.getMessage());
		}
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int col = 1; col <= cols; col++) {
            String columnName = rsmd.getColumnLabel(col);
            if (null == columnName || 0 == columnName.length()) {
                columnName = rsmd.getColumnName(col);
            }
            PropertyDescriptor pd = ctt.getPdByColumn(columnName);
            if(null!=pd){
            	Class<?> propType = pd.getPropertyType();
                Object value = processColumn(rs, columnName, propType);
                if (propType != null && value == null && propType.isPrimitive()) {
                    value = primitiveDefaults.get(propType);
                }
                callSetter(bean, pd, value);
            }
		}
        return bean;
    }
	
	
	public static <E> List<E> createListBean(ResultSet rs, 
			Class<E> cls) throws SQLException {
		ClassSwitchTable<E> ctt = new ClassSwitchTable<E>(cls);
		List<E> result = new ArrayList<E>();
		while(rs.next()){
			result.add((E) createBean(rs, ctt, cls));
		}
		return result;
	}
	
	public static <E> List<E> createListBean(ResultSet rs, 
			Class<E> cls, ClassSwitchTable<E> ctt) throws SQLException {
		List<E> result = new ArrayList<E>();
		while(rs.next()){
			result.add(createBean(rs, ctt, cls));
		}
		return result;
	}
	
	public static void callSetter(Object target, PropertyDescriptor prop, Object value) {
        Method setter = prop.getWriteMethod();
        if (setter == null) {
            return;
        }
        Class<?>[] params = setter.getParameterTypes();
        try {
        	final String targetType = params[0].getName();
            if (value instanceof java.util.Date) {
                if ("java.sql.Date".equals(targetType)) {
                    value = new java.sql.Date(((java.util.Date) value).getTime());
                } else if ("java.sql.Time".equals(targetType)) {
                    value = new java.sql.Time(((java.util.Date) value).getTime());
                } else if ("java.sql.Timestamp".equals(targetType)) {
                    value = new java.sql.Timestamp(((java.util.Date) value).getTime());
                }
            }else if(value instanceof Number){
            	if("java.lang.Integer".equals(targetType)){
            		value = ((Number)value).intValue();
            	}else if("java.lang.Long".equals(targetType)){
            		value = ((Number)value).longValue();
            	}else if("java.lang.Double".equals(targetType)){
            		value = ((Number)value).doubleValue();
            	}else if("java.lang.Float".equals(targetType)){
            		value = ((Number)value).floatValue();
            	}else if("java.math.BigDecimal".equals(targetType)){
            		value = new java.math.BigDecimal(value.toString());
            	}else if("java.math.BigInteger".equals(targetType)){
            		value = new java.math.BigInteger(value.toString());
            	}
            }
            if (isCompatibleType(value, params[0])) {
                setter.invoke(target, new Object[]{value});
            } else {
              throw new SQLException(
                  "Cannot set " + prop.getName() + ": incompatible types, cannot convert "
                  + value.getClass().getName() + " to " + params[0].getName());
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Cannot set " + prop.getName() + ": " + e.getMessage(), e);
        } 
    }
	
	public static Object callGetter(Object obj, PropertyDescriptor prop) {
		Method readMethod = prop.getReadMethod();
		if(readMethod!=null){
			try {
				Object value = readMethod.invoke(obj);
				return value;
			} catch (Exception e) {
				logger.warn("callGetter", e);
			}
		}
		return null;
	}
	
	public static boolean isCompatibleType(Object value, Class<?> type) {
        if (value == null || type.isInstance(value)) {
            return true;
        } else if (type.equals(Integer.TYPE) && Integer.class.isInstance(value)) {
            return true;
        } else if (type.equals(Long.TYPE) && Long.class.isInstance(value)) {
            return true;
        } else if (type.equals(Double.TYPE) && Double.class.isInstance(value)) {
            return true;
        } else if (type.equals(Float.TYPE) && Float.class.isInstance(value)) {
            return true;
        } else if (type.equals(Short.TYPE) && Short.class.isInstance(value)) {
            return true;
        } else if (type.equals(Byte.TYPE) && Byte.class.isInstance(value)) {
            return true;
        } else if (type.equals(Character.TYPE) && Character.class.isInstance(value)) {
            return true;
        } else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value)) {
            return true;
        }
        return false;

    }
	
	/**
	 * 填充SQL语句
	 * @param sql
	 * @param params
	 * @return
	 * hulb 2014-9-13 上午9:58:18
	 */
	public static String fullSql(String sql, Object... params) {
        StringBuilder result = new StringBuilder();
        int len = params.length;
        int currIndex = 0;
        boolean inQuote = false;
        boolean inQuote2 = false;
        char[] sqlChar = sql != null ? sql.toCharArray() : new char[]{};

        for (int i=0; i < sqlChar.length; i++){
            if (sqlChar[i] == '\''){
                inQuote = !inQuote;
            }
            if (sqlChar[i] == '"'){
                inQuote2 = !inQuote2;
            }

            if (sqlChar[i] == '?' && !(inQuote || inQuote2)){
                if (currIndex<len){
                    result.append(prettyPrint(params[currIndex]));
                    currIndex++;
                } else {
                    result.append('?');
                }
            } else {
                result.append(sqlChar[i]);
            }
        }
        return result.toString();
    }
	public static String prettyPrint(Object obj){
        StringBuilder sb = new StringBuilder();
        if (obj == null){
            sb.append("NULL");
        } else if (obj instanceof String){
        	sb.append(QUOT + obj.toString()+QUOT);
        } else if (obj instanceof Number){
        	sb.append(QUOT + obj.toString()+QUOT);
        } else if (obj instanceof Date){
        	sb.append(QUOT + (Date)obj+QUOT);
//        	sb.append(QUOT + DateTools.dateFormat((Date)obj)+QUOT);
        } else  if (obj instanceof Blob){
            sb.append(formatLogParam((Blob)obj));
        } else if (obj instanceof Clob){
            sb.append(formatLogParam((Clob)obj));
        } else if (obj instanceof Ref){
            sb.append(formatLogParam((Ref)obj));
        } else if (obj instanceof Array){
            sb.append(formatLogParam((Array)obj));
        } else {
            sb.append(obj.toString());
        }
        return sb.toString();
    }

	public void fillStatement(PreparedStatement stmt, Object... params)
			throws SQLException {
		if (params == null) {
			return;
		}
		ParameterMetaData pmd = null;
		pmd = stmt.getParameterMetaData();
		int stmtCount = pmd.getParameterCount();
		int paramsCount = params == null ? 0 : params.length;

		if (stmtCount != paramsCount) {
			throw new SQLException("Wrong number of parameters: expected "
					+ stmtCount + ", was given " + paramsCount);
		}
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				stmt.setObject(i + 1, params[i]);
			} else {
				int sqlType = Types.VARCHAR;
				try {
					sqlType = pmd.getParameterType(i + 1);
				} catch (SQLException e) {
					logger.error("", e);
				}
				stmt.setNull(i + 1, sqlType);
			}
		}
	}
   
    private static String formatLogParam(Blob obj) {
        String result="";
        try {
            result= "(blob of length "+obj.length()+")";
        } catch (SQLException e) {
            result = "(blob of unknown length)";
        }
        return result;
    }

    /** Formatter for debugging purposes only. 
     * @param obj to print
     * @return String
     */
    private static String formatLogParam(Clob obj) {
        String result="";
        try {
            result= "(cblob of length "+obj.length()+")";
        } catch (SQLException e) {
            result = "(cblob of unknown length)";
        }
        return result;
    }

    /** Formatter for debugging purposes only. 
     * @param obj to print
     * @return String
     */
    private static String formatLogParam(Array obj) {
        String result="";
        try {
            result= "(array of type"+obj.getBaseTypeName().length()+")";
        } catch (SQLException e) {
            result = "(array of unknown type)";
        }
        return result;
    }

    /** Formatter for debugging purposes only. 
     * @param obj to print
     * @return String
     */
    private static String formatLogParam(Ref obj) {
        String result="";
        try {
            result= "(ref of type"+obj.getBaseTypeName().length()+")";
        } catch (SQLException e) {
            result = "(ref of unknown type)";
        }
        return result;
    }
	
}
