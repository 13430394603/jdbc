package com.jdbc4.session;

/**
 * 
 * <b>将SessionFactory封装<b>
 * <pre>
 * 	全称EncapsulationSessionFactory或者PackageSessionFactory
 * </pre>
 * @author 威 
 * <br>2017年12月12日 下午10:28:05 
 *
 */
public class PgSessionFactory {
	private static SessionFactory sf ;
	static {
		Configuration cf = new Configuration() ;
		cf.configure() ;
		sf = cf.buildSessionFactory() ;
	}
	public static Session openSession(){
		return sf.openSession() ;
	}
}
