package com.jacob.base.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jacob.base.annoTable.Constraint;
import com.jacob.base.annoTable.GenerateKey;
import com.jacob.base.annoTable.TableColumnSQL;
import com.jacob.base.annoTable.TableSQL;

public class ClassSwitchTable<E> {
	
	protected final Logger log = Logger.getLogger(getClass());
	
	/**
	 * Key = 属性名； Value=字段名
	 */
	private Map<String, String> classTypeMap = new HashMap<String, String>();
	
	/**
	 * Key = 字段名； Value=字段描述
	 */
	private Map<String, PropertyDescriptor> columnsLowerMap = new HashMap<String, PropertyDescriptor>();
	
	private PropertyColumn[] proColArr;
	private String idName;//主键名
	private String tableName;//表名
	private GenerateKey generateKey;//主键生成策略
	private static String objectName = Object.class.getName();
	private static String baseBeanName = BaseBean.class.getName();
	
	private Class<E> cls;
	public ClassSwitchTable(Class<E> cls) {
		super();
		this.cls=cls;
		init(cls);
	}
	
	public void init(Class<?> cla){
		//表名
		if(cls.isAnnotationPresent(TableSQL.class) ){
	    	//注解了TableSQL注解
	        TableSQL ts = (TableSQL)cls.getAnnotation(TableSQL.class);
	        String taName = ts.value();
	        
	        //如果获取的值为TableSQL的默认值，则使用cls的类名来做为表名
	        if(taName!=null && !taName.trim().equals("")){
	        	this.tableName = taName;
	        }else{
	        	this.tableName = strClassToDB(cls.getSimpleName().toLowerCase());
	        }
		}else{
			this.tableName = strClassToDB(cls.getSimpleName().toLowerCase());
		}
		
		//字段名
		List<PropertyColumn> pcList = new ArrayList<PropertyColumn>();
		//继承关系最多5层
		for (int i = 0; i < 5; i++) {
			String claName = cla.getName();
			if(claName.equals(objectName) || claName.equals(baseBeanName)){
				break;
			}else{
				Field[] fields = cla.getDeclaredFields();
				pcList.addAll(initColumns(fields));
			}
			cla = cla.getSuperclass();
		}
		proColArr = new PropertyColumn[pcList.size()];
		for(int i=0; i<proColArr.length; i++){
			proColArr[i] = pcList.get(i);
		}
		
		//如果没有主键
		if(idName==null){
			log.warn(cls.getName() + "缺乏主键标识，没有主键，则不能通过Bean对象来持久化数据");
		}
		
		BeanInfo beanInfo = null;
		try {
			/*内省是 Java 语言对 Bean 类属性、事件的一种处理方法
			 * （也就是说给定一个javabean对象，我们就可以得到/调用它的所有的get/set方法）。
			 * 例如类 A中有属性 name, 那我们可以通过 getName,setName 来得到其值或者设置新的值。
			 * 通过 getName/setName 来访问 name属性，这就是默认的规则。 
			 * Java中提供了一套 API用来访问某个属性的 getter/setter方法，通过这些 API可以使你不需要了解这个规则，这些 API存放于包 java.beans 中。
			 * 
			 * 一般的做法是通过类 Introspector来获取某个对象的 BeanInfo信息，
			 * 然后通过 BeanInfo来获取属性的描述器（ PropertyDescriptor），
			 * 通过这个属性描述器就可以获取某个属性对应的 getter/setter方法，然后我们就可以通过反射机制来调用这些方法。
			*/
			beanInfo = Introspector.getBeanInfo(cls);
		} catch (IntrospectionException e) {
			log.warn(cls.getName()+"获取属性发生异常：", e);
		}
		
		if(beanInfo!=null){
			PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
			for(int i=0; i<props.length; i++) {
				String dbName = classTypeMap.get(props[i].getName());
				if(null!=dbName){
					columnsLowerMap.put(dbName.toLowerCase(), props[i]);
				}
			}
		}
	}
	
	/**
	 * 将命名转换为数据库识别的字段名
	 * @param str
	 * @return
	 */
	private String strClassToDB(String str) {
		if(str==null || str.trim().equals("")){return null;}
		//判断字符是否为大写
		Boolean flag = Character.isUpperCase(str.charAt(0));
		//将abCd ==> ab_Cd
		str = str.replaceAll("([A-Z])", ("_$1"));
		//将大写替换成小写 ab_Cd ==> ab_cd
		str = str.toLowerCase();
		if(flag){
			str = str.substring(1,str.length());
		}
		return str;
	}
	
	/**
	 * 设置表中的属性及属性对应数据库中的字段
	 * @param fields
	 * @return
	 */
	private List<PropertyColumn> initColumns(Field[] fields){
		List<PropertyColumn> list = new ArrayList<PropertyColumn>(fields.length);
		
		for(Field field : fields){
			
			//判断是否静态属性
			if(Modifier.isStatic(field.getModifiers())){
				continue;
			}
			
        	//在java的反射使用中,如果字段是私有的,那么必须要对这个字段设置 
            field.setAccessible(true);
            String fdName = field.getName();//表中的字段名
            String dbName = null;//数据库对应的字段 
            //判断是否使用注解
        	if(field.isAnnotationPresent(TableColumnSQL.class) ){
        		TableColumnSQL tcs = (TableColumnSQL)field.getAnnotation(TableColumnSQL.class);
        		dbName = tcs.value();//对应数据库的字段

        		//是否为主键
        		Constraint c = tcs.constraint();//字段对应的约束
        		boolean isPrimary = c.isPrimary();//是否为主键
        		if(isPrimary){
        			idName = fdName;
        			generateKey = c.generateKey();
        		}
        	}else{
        		//System.out.println("字段"+field.getName()+"未使用注解@TableColumnSQL！");
        		dbName = strClassToDB(fdName);//对应数据库的字段
        	}
        	
        	PropertyColumn entity = new PropertyColumn();
    		entity.setProColName(fdName);
    		entity.setSqlColName(dbName);
    		list.add(entity);
    		classTypeMap.put(fdName.toString(), dbName);
        }
		return list;
	}

	/**
	 * 通过数据库字段名获取PropertyDescriptor
	 * @param column
	 * @return
	 */
	public PropertyDescriptor getPdByColumn(String column) {
		return columnsLowerMap.get(column.toLowerCase());
	}
	
	public Map<String, String> getClassTypeMap() {
		return classTypeMap;
	}

	public void setClassTypeMap(Map<String, String> classTypeMap) {
		this.classTypeMap = classTypeMap;
	}

	public Map<String, PropertyDescriptor> getColumnsLowerMap() {
		return columnsLowerMap;
	}

	public void setColumnsLowerMap(Map<String, PropertyDescriptor> columnsLowerMap) {
		this.columnsLowerMap = columnsLowerMap;
	}

	public PropertyColumn[] getProColArr() {
		return proColArr;
	}

	public void setProColArr(PropertyColumn[] proColArr) {
		this.proColArr = proColArr;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public GenerateKey getGenerateKey() {
		return generateKey;
	}

	public void setGenerateKey(GenerateKey generateKey) {
		this.generateKey = generateKey;
	}

	public Class<E> getCls() {
		return cls;
	}

	public void setCls(Class<E> cls) {
		this.cls = cls;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{tableName=").append(tableName).append(",");
		sb.append("idName=").append(idName).append(",");
		sb.append("classTypeMap=").append(classTypeMap).append(",");
		sb.append("columnsLowerMap=").append(columnsLowerMap).append(",");
		sb.append("}");
		return sb.toString();
	}
}
