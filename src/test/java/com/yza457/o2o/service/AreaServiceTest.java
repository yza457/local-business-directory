package com.yza457.o2o.service;

import com.yza457.o2o.BaseTest;
import com.yza457.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService; // this will call the implementation of AreaService interface

    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        assertEquals("Vancouver", areaList.get(0).getAreaName());
    }
}
