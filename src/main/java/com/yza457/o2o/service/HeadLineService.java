package com.yza457.o2o.service;

import com.yza457.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
//    public static final String HLLISTKEY = "headlinelist";

    /**
     *
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
