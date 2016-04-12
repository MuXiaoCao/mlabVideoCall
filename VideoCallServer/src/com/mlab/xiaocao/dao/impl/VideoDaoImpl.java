package com.mlab.xiaocao.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mlab.xiaocao.dao.VideoDao;

@Repository("videoDao")
public class VideoDaoImpl implements VideoDao{
	
	@Autowired(required=true)
	@PersistenceContext(name="unitName")
	private EntityManager entityManager;
	
	
}
