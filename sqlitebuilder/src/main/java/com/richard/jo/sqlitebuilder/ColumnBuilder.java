package com.richard.jo.sqlitebuilder;

import java.util.ArrayList;
import java.util.List;

public class ColumnBuilder {

    private List<String> createColumnCommand;
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String AUTOINCREMENT = "AUTOINCREMENT";

    public ColumnBuilder(String columnName, String columnType){
        createColumnCommand = new ArrayList<>();
        createColumnCommand.add(columnName);
        createColumnCommand.add(columnType);
    }

    public ColumnBuilder setPrimaryKey(){
        createColumnCommand.add(PRIMARY_KEY);
        return this;
    }

    public ColumnBuilder setAutoIncrement(){
        createColumnCommand.add(AUTOINCREMENT);
        return this;
    }

    public String execute(){
        String ret = "";
        for(int i = 0; i<createColumnCommand.size(); i++){
            if(i != 0){
                ret = ret + " ";
            }
            ret = ret + createColumnCommand.get(i);
        }
        return ret;
    }
}
