package com.jacob.tools;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class GetContext{
	
	/**
	 * @return
	 * @throws MyException 
	 * @throws NamingException
	 */
	public static Context Factory() throws NamingException{
		Properties pro = new Properties();
		pro.setProperty(Context.INITIAL_CONTEXT_FACTORY,PropertiesInfo.getValue("INITIAL_CONTEXT_FACTORY"));
		pro.setProperty(Context.URL_PKG_PREFIXES,PropertiesInfo.getValue("INITIAL_CONTEXT_FACTORY"));
		pro.setProperty(Context.PROVIDER_URL,PropertiesInfo.getValue("PROVIDER_URL"));
		return new InitialContext(pro);
	}
}
