package com.yza457.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>(); // to be thread safe

    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE = "slave";

    /**
     * get dbType of the thread
     * @return
     */
    public static String getDbType() {
        String db = contextHolder.get();
        if (db == null) {
            db = DB_MASTER;
        }
        return db;
    }

    /**
     * set dbType of the thread
     * @param str
     */
    public static void setDbType(String str) {
        logger.debug("The data source to use is: " + str);
        contextHolder.set(str);
    }

    /**
     * clean connection type
     */
    public static void clearDbType() {
        contextHolder.remove();
    }

}
