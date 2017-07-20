package com.jacob.base.annoTable;

/**
 * 主键生成规则
 * @author jacob
 *
 */
public enum GenerateKey {
	/**
	 * 自增长性，主要用于MySql、Sql Server、DB2中主键生成机制 
	 */
	IDENTITY,
	
	/**
	 * 采用数据库提供的sequence 机制生成主键。如Oralce 中的Sequence
	 */
	SEQUENCE,
	
	/**
	 * 用户自己定义 
	 */
	ASSIGNED,
	
	/**
	 * 基于128 位唯一值产生算法生成16 进制数值（编码后以长度32 的字符串表示）作为主键
	 */
	UUID
}
