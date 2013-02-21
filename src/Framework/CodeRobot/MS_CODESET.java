/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.CodeRobot;

import Framework.Data.DbObject;

/**
 *
 * @author Administrator
 * @date 2010/4/6, 下午 02:56:29
 */
public class MS_CODESET extends DbObject {

    String _TableName = "MS_CODESET";
    String _DatabaseName = "B2B";
    String _DataSourceName = "MS_CODESET";

    @Override
    public String getTableName() {
        return _TableName;
    }

    @Override
    public void setTableName(String _TableName) {
        this._TableName = _TableName;
    }

    @Override
    public String getDataSourceName() {
        return _DataSourceName;
    }

    @Override
    public void setDataSourceName(String _DataSourceName) {
        this._DataSourceName = _DataSourceName;
    }

    @Override
    public String getDatabaseName() {
        return _DatabaseName;
    }

    @Override
    public void setDatabaseName(String _DatabaseName) {
        this._DatabaseName = _DatabaseName;
    }
}
