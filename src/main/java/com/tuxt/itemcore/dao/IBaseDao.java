package com.tuxt.itemcore.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ai.frame.bean.InputObject;

/**
 * Dao接口基类
 */
public interface IBaseDao {
	  public static final String MYBATIS_DAO = "daoMybatis";
	  /**
	   * 查询全部数据  list
	   * @param namespace
	   * @param statement
	   * @return
	   */
	  public  List<Map<String,Object>> query(String namespace,String statement);
	  /**
	   * 分页查询   list
	   * @param namespace
	   * @param statement
	   * @param limit
	   * @param offset
	   * @return
	   */
	  public  List<Map<String,Object>> query(String namespace,String statement,int limit, int offset);
      /**
       * 根据条件查询   list
       * @param namespace
       * @param statement
       * @param paramData
       * @return
       */
	  public  List<Map<String,Object>> query(String namespace,String statement, Map<String,Object> paramData);
      /**
       * 根据条件分页查询
       * @param namespace
       * @param statement
       * @param paramData
       * @param limit
       * @param offset
       * @return
       */
	  public  List<Map<String,Object>> query(String namespace,String statement,Map<String,Object> paramData, int limit, int offset);
      /**
       * 查询总数
       * @param namespace
       * @param statement
       * @return
       */
	  public  int count(String namespace,String statement);
      /**
       * 根据条件查询总数 
       * @param namespace
       * @param statement
       * @param paramData
       * @return
       */
	  public  int count(String namespace,String statement, Map<String,Object> paramData);
      /**
       * 根据条件查询 Map
       * @param namespace
       * @param statement
       * @param paramData
       * @return
       */
	  public  Map<String,Object> get(String namespace,String statement, Map<String,Object> paramData);

	  public  Map<String,Object> load(String namespace, String key, String value);

	  public  void insert(String namespace,String statement, Map<String,Object> paramData);

	  public  int  update(String namespace,String statement, Map<String,Object> paramData);

	  public  int  delete(String namespace,String statement, Map<String,Object> paramData);
	  
	  public  String getSql(String namespace,String statement, Map<String,Object> paramData);
	  /**
	   * mybatise 
	   * @param namespace
	   * @param statement
	   * @param in
	   */
	  public void batchInsertOrm( String namespace,String statement, final InputObject in);
	  /**
	   * mybatise 
	   * @param namespace
	   * @param statement
	   * @param in
	   */
	  public void batchUpdateOrm( String namespace,String statement, final InputObject in);
	  
	  /**
	   * mybatise 
	   * @param namespace
	   * @param statement
	   * @param in
	   */
	  public void batchDeleteOrm( String namespace,String statement, final InputObject in);
	  
	  /**
	     * 批量更新
	     * 方法描述：批量更新（效率没有在配置文件上的高）建议在配置文件中配置
	     * @param statementName
	     * @param list
	     * @throws DataAccessException
	     * @comment
	     */
	  public void batchUpdate( String namespace,String statement, final InputObject in)  throws DataAccessException;
	  /**
	     * 批量插入
	     * 方法描述：批量插入（效率没有在配置文件上的高）建议在配置文件中配置
	     * @param statement
	     * @param list
	     * @throws DataAccessException
	     * @date 2013-4-3 上午11:27:39
	     * @comment
	     */
	    public void batchInsert(String namespace,String statement, final InputObject in)  throws DataAccessException;
	    /**
	     * 批量删除
	     * 方法描述：批量删除（效率没有在配置文件上的高） 建议在配置文件中配置
	     * @param statement
	     * @param in
	     * @throws DataAccessException
	     * @date 2013-4-3 上午11:29:53
	     * @comment
	     */
	    public void batchDelete( String namespace,String statement, final InputObject in)  throws DataAccessException;

}
