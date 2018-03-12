package com.hzwl.dao.impl;

import org.springframework.stereotype.Repository;

import com.hzwl.dao.ICarDao;
import com.hzwl.dao.base.impl.BaseDaoImpl;
import com.hzwl.entity.Car;

@Repository
public class CarDaoImpl extends BaseDaoImpl<Car> implements ICarDao{

}
