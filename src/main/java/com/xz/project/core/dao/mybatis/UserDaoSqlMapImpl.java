/**   
* @Title: UserDaoSqlMapImpl.java 
* @Package: com.xz.project.core.dao.mybatis 
* @Description: 用户Dao实现类
* @author: davidwan
* @date: 2014-7-14 上午10:26:56 
* @version: V1.0   
*/
package com.xz.project.core.dao.mybatis;
 
import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl; 
import com.xz.project.core.dao.UserDao;
import com.xz.project.core.domain.entity.User; 

@Repository
public class UserDaoSqlMapImpl extends BaseDaoSqlMapImpl<User> implements UserDao{

 
}
