package com.yza457.o2o.dao;

import com.yza457.o2o.entity.Area;

import java.util.List;

public interface AreaDao {
    /**
     * list all areas
     * @return areaList
     */
    List<Area> queryArea();
}
