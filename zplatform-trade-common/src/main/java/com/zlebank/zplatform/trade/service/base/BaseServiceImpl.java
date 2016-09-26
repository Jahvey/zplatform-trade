/* 
 * BaseServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.service.IBaseService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 下午2:53:18
 * @since 
 */
public abstract class BaseServiceImpl<E extends Serializable, E_PK extends Serializable> implements IBaseService<E, E_PK>{
    private static final Log log = LogFactory.getLog(BaseServiceImpl.class);
    public  static final String ENCODING = "UTF-8";
    public abstract Session getSession();
    
    public void delete(E entity) {
        getSession().delete(entity);
    }

    public List<E> findAll() {
       
        try {
            String queryString = "from " + getPersistentClass().getName();

            return getSession().createQuery(queryString).list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List<E> findByProperty(String propertyName, Object value) {
        try {
            String queryString = "from " + getPersistentClass().getName()
                    + " as model where model." + propertyName + "= ?";
            List<E> resultList = getSession().createQuery(queryString).setParameter(0,
                    value).list();
            
            return resultList;
            // return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            re.printStackTrace();
            throw re;
        }
    }

    public E get(E_PK id) {
        try {
            E entity = (E) getSession().get(getPersistentClass(),
                    (Serializable) id);
            if(entity!=null){
                getSession().evict(entity);
            }
            return entity;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<E> getNamedQuery(String queryName, Object[] paramaters) {
        return null;
    }
    
    public E getUniqueByHQL(String queryString, Object[] paramaters) {
        log.info("queryString is " + queryString);
        Query query = null;
        Session session = getSession();
        E result = null;
        try {
            query = session.createQuery(queryString);
            if (paramaters != null) {
                for (int i = 0; i < paramaters.length; i++) {
                    query.setParameter(i, paramaters[i]);
                }
            }
            result = (E) query.uniqueResult();
            if(result!=null){
                session.evict(result);
            }
        } catch (RuntimeException e) {
            log.error("find error", e);
            throw e;
        }

        return result;
    }

    public E getUniqueBySQL(String queryString, Object[] paramaters) {
        E result = null;
        try {
            Session session = getSession();
            SQLQuery query = session.createSQLQuery(queryString);
            if (paramaters != null) {
                for (int i = 0; i < paramaters.length; i++) {
                    query.setParameter(i, paramaters[i]);
                }
            }
            result = (E) query.addEntity(getPersistentClass()).uniqueResult();
            if(result!=null){
                session.evict(result);
            }
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public List<?> queryByHQL(String queryString,Object[] paramaters ) {
        List<?> result = null;
        try {
            log.info("queryString:"+queryString);
            Query query = getSession().createQuery(queryString);
            if (paramaters != null) {
                for (int i = 0; i < paramaters.length; i++) {
                    query.setParameter(i, paramaters[i]);
                    log.info("paramaters "+i+":"+paramaters[i]);
                }
            }
            result = query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public List<?> queryByHQL(String queryString, String paramaterName,
            Collection<?> paramater) {
        log.info("queryString is " + queryString);
        Query query = null;
        List<?> result = null;
        try {
            query = getSession().createQuery(queryString);
            query.setParameterList(paramaterName, paramater);
            result = query.list();
           
        } catch (HibernateException e) {
            log.error("query error ", e);
            throw e;
        }
        return result;
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public List<?> queryBySQL(String queryString,Object[] paramaters) {
        List<?> resultList = null;
        try {
            Session session = getSession();
            SQLQuery query = (SQLQuery) session.createSQLQuery(queryString)
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            if (null != paramaters) {
                for (int i = 0; i < paramaters.length; i++) {
                    query.setParameter(i, paramaters[i]);
                }
            }
            resultList = query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return resultList;
    }

    public List<?> queryBySQL(String queryString, String paramaterName,
            Collection<?> paramater) {
        log.info("queryString is " + queryString);
        SQLQuery query = null;
        List<?> resultList = null;
        try {
            Session session = getSession();
            query = (SQLQuery) session.createSQLQuery(queryString)
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            query.setParameterList(paramaterName, paramater);
            resultList = query.list();
           
        } catch (HibernateException e) {
            log.error("sql query error", e);
            throw e;
        }
        return resultList;
    }

    public void save(E entity) {
        getSession().save(entity);
    }
    
    public Long saveEntity(E entity){
        Serializable result = getSession().save(entity);
        Long id = (Long)result;
        return id;
    }

    public void save(List<E> entity) {
        getSession().save(entity);
    }

    public void saveOrUpdate(E entity) {
        getSession().saveOrUpdate(entity);
    }

    public void update(E entity) {
        getSession().update(entity);
    }

    public void update(List<E> entity) {
        getSession().update(entity);
    }

    public int updateByHQL(String queryString,Object[] paramaters) {
        try {
            Session session = getSession();
            Query query = session.createQuery(queryString);
            if (paramaters != null) {
                for (int i = 0; i < paramaters.length; i++) {
                    query.setParameter(i, paramaters[i]);
                }
            }
            return query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
        
    }

    public int updateByHQL(String queryString, String paramaterName,
            Collection<?> paramater) {
        try {
            Session session = getSession();
            Query query = session.createQuery(queryString);
            query.setParameterList(paramaterName, paramater);
            return query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public List<E> findByProperty(Map<String, Object> variable) {
        try {
            String queryString = "from " + getPersistentClass().getName()
                    + " as model where ";
            int size = variable.size();
            Object[] value = new Object[variable.size()];
            int i = 0;
            for (String key : variable.keySet()) {
                queryString += "model." + key + "= ? and ";
                value[i] = variable.get(key);
                i++;
            }
            queryString += "1=1";
            Query queryObject = getSession().createQuery(queryString);
            if (value != null) {
                for (int j = 0; j < value.length; j++) {
                    queryObject.setParameter(j, value[j]);
                }
            }
            List<E> resultList = queryObject.list();
            if(resultList.size()>0){
                getSession().evict(resultList);
            }
            return resultList;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<?> executeBySQL(String queryString, Object[] paramaters) {
    	log.info("queryString is " + queryString);
        SQLQuery query = null;
        List<?> resultList = null;
        try {
            Session session = getSession();
            query = (SQLQuery) session.createSQLQuery(queryString);
            if (paramaters != null) {
                for (int i = 0; i < paramaters.length; i++) {
                    query.setParameter(i, paramaters[i]);
                }
            }
            query.executeUpdate();
           
        } catch (HibernateException e) {
            log.error("sql query error", e);
            throw e;
        }
        return resultList;
        
    }
    public List<Map<String, Object>> executeOracleProcedure(String queryString,String[] columns, Object[] paramaters,String cursor){
        return null;
    }
    private Class<E> getPersistentClass() {
        Class<E> clazz = getGenericClass(getClass(), 1);
        if (null == clazz)
            throw new RuntimeException(
                    "concreate class must provide entity type or the type is incorrect!");
        return clazz;
    }
    
    private <T> Class<T> getGenericClass(final Class baseClass, final int index) {
        Type genericType = baseClass.getGenericSuperclass();

        if (genericType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genericType)
                    .getActualTypeArguments();
            if (null != params && params.length >= index) {
                if (params[index - 1] instanceof Class)
                    return (Class<T>) params[index - 1];
            }
        }
        return null;
    }
    
}
