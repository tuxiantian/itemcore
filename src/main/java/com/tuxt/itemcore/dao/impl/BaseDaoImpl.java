package com.tuxt.itemcore.dao.impl;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataAccessException;

import com.ai.frame.bean.InputObject;
import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.itemcore.dao.IBaseDao;
import com.tuxt.itemcore.util.DateUtil;

/**
 * 
 * 
 */
public class BaseDaoImpl implements IBaseDao {
	private static final Logger logger = LoggerFactory.getDaoLog(BaseDaoImpl.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SqlSessionTemplate sqlSessionTemplate;

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Map<String, Object>> query(String namespace,String statement) {
		long bengin = System.currentTimeMillis();
		List<Map<String, Object>> dataList = sqlSessionTemplate.<Map<String,Object>>selectList(changeStatement(namespace,statement));
		logger.info("query","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return dataList;
	}

	public List<Map<String, Object>> query(String namespace,String statement,Map<String, Object> paramData) {
		long bengin = System.currentTimeMillis();
		List<Map<String, Object>> dataList = sqlSessionTemplate.<Map<String,Object>>selectList(changeStatement(namespace,statement),paramData);
		logger.info("query","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return dataList;
	}

	public List<Map<String, Object>> query(String namespace,String statement,Map<String, Object> paramData, int limit, int offset) {
		long bengin = System.currentTimeMillis();
		RowBounds rowBounds = new RowBounds((offset-1)*limit, limit);
		List<Map<String, Object>> dataList = sqlSessionTemplate.<Map<String,Object>>selectList(changeStatement(namespace,statement),paramData,rowBounds);
		logger.info("query","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return dataList;
	}


	public int count(String namespace,String statement) {
		long bengin = System.currentTimeMillis();
		int i=sqlSessionTemplate.<Integer>selectOne(changeStatement(namespace,statement));
		logger.info("count","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return i;
	}

	
	public int count(String namespace,String statement, Map<String, Object> paramData) {
		long bengin = System.currentTimeMillis();
		int i=sqlSessionTemplate.<Integer>selectOne(changeStatement(namespace,statement),paramData);
		logger.info("count","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return i;
	}


	public Map<String, Object> get(String namespace,String statement,Map<String, Object> paramData) {
		long bengin = System.currentTimeMillis();
		Map<String, Object> dataMap =  sqlSessionTemplate.<Map<String, Object>>selectOne(changeStatement(namespace,statement),paramData);
		logger.info("get","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return dataMap;
	}


	public Map<String, Object> load(String namespace, String key,
			String value) {
		long bengin = System.currentTimeMillis();
		Map<String,String> param = new HashMap<String,String>();
		param.put(key, value);
		Map<String, Object> dataMap = sqlSessionTemplate.<Map<String, Object>>selectOne(changeStatement(namespace,"load"),param);
		logger.info("load","["+namespace+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return dataMap;
	}

	
	public void insert(String namespace,String statement, Map<String, Object> paramData) {
		long bengin = System.currentTimeMillis();
		sqlSessionTemplate.insert(changeStatement(namespace,statement), paramData);
		logger.info("insert","["+namespace+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
	}

	public void insertBatch(String namespace,String statement,List  <Map<String, Object>> list) {
		long bengin = System.currentTimeMillis();
		sqlSessionTemplate.insert(changeStatement(namespace,statement), list);
		logger.info("insert","["+namespace+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
	}
	public int update(String namespace,String statement, Map<String, Object> paramData) {
		long bengin = System.currentTimeMillis();
		int i=sqlSessionTemplate.update(changeStatement(namespace,statement), paramData);
		logger.info("update","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return i;
	}

	
	public int delete(String namespace,String statement, Map<String, Object> paramData) {
		long bengin = System.currentTimeMillis();
		int i= sqlSessionTemplate.delete(changeStatement(namespace,statement), paramData);
		logger.info("delete","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return i;
	}
	
	private String changeStatement(String namespace,String statement){
		return namespace + "." + statement;
	}

	@SuppressWarnings("unused")
	private void changeDateType(List<Map<String,Object>> dataList){
		if(dataList == null || dataList.size() == 0){
			return;
		}
		
		for(Map<String,Object> map : dataList){
			for(Map.Entry<String, Object> entry : map.entrySet()){
				if(entry.getValue().getClass() == Timestamp.class){
					map.put(entry.getKey(), sdf.format((Timestamp)entry.getValue()));
				}
			}
		}
		
	}
	
	@SuppressWarnings("unused")
	private void changeDateType(Map<String,Object> dataMap){
		if(dataMap == null || dataMap.size() == 0){
			return;
		}
		
		for(Map.Entry<String, Object> entry : dataMap.entrySet()){
			if(entry.getValue().getClass() == Timestamp.class){
				dataMap.put(entry.getKey(),  sdf.format((Timestamp)entry.getValue()));
			}
		}
			
	}

	public List<Map<String, Object>> query(String namespace, String statement,
			int limit, int offset) {
		long bengin = System.currentTimeMillis();
		RowBounds rowBounds = new RowBounds((offset-1)*limit, limit);
		List<Map<String, Object>> dataList = sqlSessionTemplate.<Map<String,Object>>selectList(changeStatement(namespace,statement),null,rowBounds);
		logger.info("query","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return dataList;
	}


	public String getSql(String namespace, String statement,Map<String, Object> paramData) {
		long bengin = System.currentTimeMillis();
		MappedStatement ms = sqlSessionTemplate.getConfiguration().getMappedStatement(namespace+"."+statement);
		BoundSql boundSql = ms.getBoundSql(paramData);
		logger.info("getSql","["+namespace+"."+statement+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		return boundSql.getSql();
	}
	
	
	
	 public void batchUpdate( String namespace,String statement, final InputObject in)  throws DataAccessException{
	        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	        try{
	        	List<Map<String,Object>> list=in.getBeans();
	            if(null != list &&  list.size() > 0){
	                int size = 100;
	                for (int i = 0, n = list.size(); i < n; i++) {
	                    session.update(changeStatement(namespace,statement), list.get(i));
	                    if (i!=0 && i % size == 0 ) {
	                        //手动每1000个一提交，提交后无法回滚
	                        session.commit();
	                        //清理缓存，防止溢出
	                        session.clearCache();
	                    }
	                }
	                session.commit();
                 //清理缓存，防止溢出
                 session.clearCache();
	            }
	        }catch (Exception e){
	            session.rollback();
	            logger.error("batchUpdate", "批量更新发生异常", e);
	        } finally {
	            session.close();
	        }
	    }
	     
	    public void batchInsert( String namespace,String statement, final InputObject in){
	        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	        List<Map<String,Object>> list=in.getBeans();
	        int size=1000;
	        try{
	            if(null != list &&  list.size() > 0){
	                for (int i = 0, n = list.size(); i < n; i++) {
	                    session.insert(changeStatement(namespace,statement), list.get(i));
	                    if (i!=0 && i % size == 0) {
	                        //手动每1000个一提交，提交后无法回滚
	                        session.commit(); 
	                        //清理缓存，防止溢出
	                        session.clearCache();
	                    }
	                }
	                session.commit();
                 //清理缓存，防止溢出
                 session.clearCache();
	            }
	        }catch (Exception e){
	        	 session.rollback();
	        	logger.error("batchInsert", "批量插入发生异常", e);
	        } finally {
	            session.close();
	        }
	    }
	    
	 
	    public void batchDelete( String namespace,String statement, final InputObject in)  throws DataAccessException{
	        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	        List<Map<String,Object>> list=in.getBeans();
	        int size = 1000;
	        try{
	            if(null != list && list.size() > 0){
	                for (int i = 0, n = list.size(); i < n; i++) {
	                	 session.delete(changeStatement(namespace,statement), list.get(i));
	                    if (i!=0 &&  i % size == 0) {
	                        //手动每1000个一提交，提交后无法回滚
	                        session.commit();
	                        //清理缓存，防止溢出
	                        session.clearCache();
	                    }
	                }
	                session.commit();
                 //清理缓存，防止溢出
                 session.clearCache();
	            }
	        }catch (Exception e){
	            session.rollback();
	            logger.error("batchDelete", "批量删除发生异常", e);
	        } finally {
	            session.close();
	        }
	    }
	    
	public void batchInsertOrm(String namespace, String statement,
			InputObject in) {
		long bengin = System.currentTimeMillis();
		sqlSessionTemplate.insert(changeStatement(namespace,statement), in.getBeans());
		logger.info("batchInsertOrm","["+namespace+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		
	}

	public void batchUpdateOrm(String namespace, String statement,
			InputObject in) {	
		/*List<Map<String, Object>> items=new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("personCode", "sys");
		map.put("makeDate",DateUtil.date2String(new Date()));
		map.put("itemid", 2);
		items.add(map);
		Map<String, Object> map2=new HashMap<>();
		map2.put("personCode", "sys");
		map2.put("makeDate",DateUtil.date2String(new Date()));
		map2.put("itemid", 3);*/
		long bengin = System.currentTimeMillis();
		sqlSessionTemplate.update(changeStatement(namespace,statement), in.getBeans());//
		logger.info("batchUpdateOrm","["+namespace+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
		
	}

	public void batchDeleteOrm(String namespace, String statement,
			InputObject in) {
		long bengin = System.currentTimeMillis();
		sqlSessionTemplate.delete(changeStatement(namespace,statement), in.getBeans());
		logger.info("batchDeleteOrm","["+namespace+"] execute, cost:"+(System.currentTimeMillis() - bengin) + "ms");
	}
	
}