package com.richard.jo.sqlitebuilder;

import java.util.ArrayList;
import java.util.List;

public class TableBuilder {
    //sqlite default is ABORT
    enum OnConflictClause{ROLLBACK, ABORT, FAIL, IGNORE, REPLACE}

    private static final String COLUMN_SEPARATOR = ", ";
    private static final String SPACE = " ";

    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String OPEN_PARENTHESIS = "(";
    private static final String TEXT_COLUMN = "TEXT";
    private static final String INTEGER_COLUMN = "INTEGER";
    private static final String LONG_COLUMN = "LONG";
    private static final String REAL_COLUMN = "REAL";
    private static final String UNIQUE = "UNIQUE";
    private static final String ON_CONFLICT = "ON CONFLICT";
    private static final String CLOSED_PARENTHESIS = ")";
    private static final String FOREIGN_KEY = "FOREIGN KEY";
    private static final String REFERENCES = "REFERENCES";
    private static final String ON_DELETE_CASCADE = "ON DELETE CASCADE";

    private List<String> createTableCommand;

    public TableBuilder(String tableName){
        createTableCommand = new ArrayList<>();
        createTableCommand.add(CREATE_TABLE + SPACE + tableName + OPEN_PARENTHESIS);
        createTableCommand.add(CLOSED_PARENTHESIS);
    }


    public TableBuilder addAutoIncrementIntegerPrimaryKeyColumn(String columnName){
        insertNewCommand(new ColumnBuilder(columnName, INTEGER_COLUMN).setPrimaryKey().setAutoIncrement().execute());
        return this;
    }

    public TableBuilder addTextColumn(String columnName){
        insertNewCommand(new ColumnBuilder(columnName, TEXT_COLUMN).execute());
        return this;
    }

    public TableBuilder addIntegerColumn(String columnName){
        insertNewCommand(new ColumnBuilder(columnName, INTEGER_COLUMN).execute());
        return this;
    }

    public TableBuilder addLongColumn(String columnName){
        insertNewCommand(new ColumnBuilder(columnName, LONG_COLUMN).execute());
        return this;
    }

    public TableBuilder addRealColumn(String columnName){
        insertNewCommand(new ColumnBuilder(columnName, REAL_COLUMN).execute());
        return this;
    }

    private void insertNewCommand(String command){
        createTableCommand.add(createTableCommand.size() - 1, command);
    }

    public TableBuilder addUniqueConstraint(String columnName, OnConflictClause onConflictClause){
        List<String> columnNames = new ArrayList<>();
        columnNames.add(columnName);
        return addUniqueConstraint(columnNames, onConflictClause);
    }

    public TableBuilder addUniqueConstraint(List<String> columnNames, OnConflictClause onConflictClause){
        String columnNamesStr = "";
        int size = columnNames.size();
        for(int i = 0; i<size; i++){
            columnNamesStr =  columnNamesStr  + columnNames.get(i);
            if(i!=size-1){
                columnNamesStr = columnNamesStr + COLUMN_SEPARATOR;
            }
        }

        String uniqueConstraint = UNIQUE + OPEN_PARENTHESIS + columnNamesStr + CLOSED_PARENTHESIS + SPACE + ON_CONFLICT + SPACE + onConflictClause.name();
        insertNewCommand(uniqueConstraint);
        return this;
    }


    public TableBuilder addForeignKey(String columnName, String foreignTableName, String foreignColumnName, boolean onDeleteCascade){
        String foreignKey = FOREIGN_KEY + OPEN_PARENTHESIS + columnName + CLOSED_PARENTHESIS + SPACE + REFERENCES + SPACE +
                foreignTableName + OPEN_PARENTHESIS + foreignColumnName + CLOSED_PARENTHESIS;

        if(onDeleteCascade){
            foreignKey = foreignKey + SPACE + ON_DELETE_CASCADE;
        }

        insertNewCommand(foreignKey);
        return this;
    }



    public String execute(){
        String ret = "";
        int size = createTableCommand.size();
        for(int i = 0; i<size; i++){
            ret = ret + createTableCommand.get(i);
            if(i!=0 && i!=size-1 && i!= size-2){
                ret = ret + COLUMN_SEPARATOR;
            }
        }
        return ret;
    }
}
