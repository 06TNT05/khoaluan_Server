/**
 * Time creation: Dec 1, 2022, 10:59:31 PM
 * 
 * Package name: com.exam.interfaces
 */
package com.exam.interfaces;

import java.util.List;

/**
 * @author Tien Tran
 *
 * interface ObjectDAO
 */
public interface ObjectDAO<T> {
	
	T findById(String id);
     
	void addObject(T object);
     
	void updateObject(T object);
 
	List<T> findAllObjects();
	
	void removeObjects(String queryString, Byte status);
}
