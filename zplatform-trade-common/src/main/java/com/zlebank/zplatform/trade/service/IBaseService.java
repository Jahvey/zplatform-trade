package com.zlebank.zplatform.trade.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IBaseService<E extends Serializable, E_PK extends Serializable> {
	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(final E entity);
	/**
	 * 保存多个实体
	 * @param entity
	 */
	public void save(final List<E> entity);
	/**
	 * 保存实体返回主键
	 * @param entity
	 * @return 主键
	 */
	public Long saveEntity(E entity);
	/**
	 * 更新实体
	 * @param entity
	 */
	public void update(final E entity);
	/**
	 * 更新多个实体
	 * @param entity
	 */
	public void update(final List<E> entity);
	/**
	 * 保存或更新实体
	 * @param entity
	 */
	public void saveOrUpdate(final E entity);
	/**
	 * 删除实体
	 * @param entity
	 */
	public void delete(final E entity);
	/**
	 * 通过主键获取实体
	 * @param id 标示
	 * @return 实体类
	 */
	public E get(final E_PK id);
	
	public List<E> getNamedQuery(String queryName, Object[] paramaters);
	/**
	 * 执行HQL语句取得唯一实体
	 * @param queryString HQL语句
	 * @param paramaters 查询参数
	 * @return
	 */
	public E getUniqueByHQL(String queryString, Object[] paramaters);
	/**
	 * 执行SQL语句取得唯一实体
	 * @param queryString SQL语句
	 * @param paramaters 查询参数
	 * @return
	 */
	public E getUniqueBySQL(String queryString, Object[] paramaters);
	/**
	 * 取得全部实体
	 * @return
	 */
	public List<E> findAll();
	/**
	 * 通过属性名称取得实体集合
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return
	 */
	public List<E> findByProperty(final String propertyName, final Object value);
	/**
	 * 通过多个属性名称取得实体集合
	 * @param variable 属性名称及其值得集合
	 * @return
	 */
	public List<E> findByProperty(final Map<String, Object> variable);
	/**
	 * 通过SQL语句取得实体集合
	 * @param queryString SQL语句
	 * @param paramaters 参数数组
	 * @return
	 */
	public List<?> queryBySQL(
			final String queryString,final Object[] paramaters);
	/**
	 * 通过SQL语句取得实体集合（用于SQL语句中含有in的查询）
	 * @param queryString 带有一个in的SQL语句
	 * @param paramaterName 属性名称
	 * @param paramater 属性值得集合
	 * @return
	 */
	public List<?> queryBySQL(String queryString,String paramaterName,Collection<?> paramater);
	/**
	 * 通过HQL语句取得实体集合
	 * @param queryString HQL语句
	 * @param paramaters 参数数组
	 * @return
	 */
	public List<?> queryByHQL(
			final String queryString,final Object[] paramaters);
	/**
	 * 通过HQL语句进行更新操作（用于HQL语句中含有in�?	 
	 * @param queryString
	 * @param paramaterName
	 * @param paramater
	 */
	public List<?> queryByHQL(final String queryString,final String paramaterName,final Collection<?> paramater);
	/**
	 * 使用SQL语句取得实体集（其中包含提交事务的语句（如存储过程）	 
	 * @param queryString
	 * @param paramaters
	 * @return
	 */
	public List<?> executeBySQL(final String queryString,final Object[] paramaters);
	/**
	 * 通过HQL语句进行更新操作
	 * @param queryString
	 * @param paramaters
	 */
	public int updateByHQL(final String queryString,final Object[] paramaters);
	/**
	 * 通过HQL语句进行更新操作（用于HQL语句中含有in�?	 * @param queryString
	 * @param paramaterName
	 * @param paramater
	 */
	public int updateByHQL(final String queryString,final String paramaterName,final Collection<?> paramater);
	/**
	 * 执行Oracle的存储过
	 * @param queryString
	 * @param paramaters
	 * @return
	 */
	public List<Map<String, Object>> executeOracleProcedure(String queryString,String[] columns, Object[] paramaters,String cursor);
	/*public PaginationSupport<?> findPaginationSupport(final String queryString,
			final Object[] paramaters, final int page, final int pageSize);
	
	public PaginationSupport<?> findPaginationSupportBySQL(final String queryString,
			final Object[] paramaters, final int page, final int pageSize);*/
}
