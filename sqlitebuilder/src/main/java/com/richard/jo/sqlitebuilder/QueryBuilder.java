package com.richard.jo.sqlitebuilder;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private static final String EQUAL = "=";
    private static final String GREATER_THAN = ">";
    private static final String GREATER_OR_EQUAL = ">=";
    private static final String LESSER_THAN = "<";
    private static final String LESS_OR_EQUAL_THAN = "<=";
    private static final String OR = "OR";
    private static final String AND = "AND";
    private static final String NOT_EQUAL = "!=";
    private static final String SPACE = " ";
    private static final String SINGLE_QUOTE = "'";
    private static final String LIKE = "LIKE";
    private static final String LIKE_WILDCARD = "%";
    private static final String NOT_LIKE = "NOT LIKE";
    private static final String WHERE = "WHERE";
    private static final String SELECT = "SELECT";
    private static final String ALL = "*";
    private static final String FROM = "FROM";

    private List<String> expressions;

    public QueryBuilder(){
        expressions = new ArrayList<>();
    }

    public QueryBuilder whereEqual(String columnName, int value){
        StringBuilder expression = new StringBuilder(columnName).append(EQUAL).append(value);
        expressions.add(expression.toString());
        return this;
    }

    public QueryBuilder whereEqual(String columnName, String value){
        StringBuilder expression = new StringBuilder(columnName).append(EQUAL).append(SINGLE_QUOTE).append(value).append(SINGLE_QUOTE);
        expressions.add(expression.toString());
        return this;
    }


    public QueryBuilder whereLike(String columnName, String value){
        StringBuilder expression = new StringBuilder(columnName).append(SPACE).append(LIKE).append(SPACE).append(SINGLE_QUOTE).append(LIKE_WILDCARD).append(value).append(LIKE_WILDCARD).append(SINGLE_QUOTE);
        expressions.add(expression.toString());
        return this;
    }

    public QueryBuilder whereNotLike(String columnName, String value){
        StringBuilder expression = new StringBuilder(columnName).append(SPACE).append(NOT_LIKE).append(SPACE).append(SINGLE_QUOTE).append(LIKE_WILDCARD).append(value).append(LIKE_WILDCARD).append(SINGLE_QUOTE);
        expressions.add(expression.toString());
        return this;
    }

    public QueryBuilder OR(){
        StringBuilder expression = new StringBuilder(SPACE).append(OR).append(SPACE);
        expressions.add(expression.toString());
        return this;
    }

    public QueryBuilder AND(){
        StringBuilder expression = new StringBuilder(SPACE).append(AND).append(SPACE);
        expressions.add(expression.toString());
        return this;
    }

    public QueryBuilder whereQuery(String query){
        StringBuilder expression = new StringBuilder(SPACE).append(WHERE).append(SPACE).append(query);
        expressions.add(expression.toString());
        return this;
    }

    public QueryBuilder andQuery(String query){
        StringBuilder expression = new StringBuilder(SPACE).append(AND).append(SPACE).append(query);
        expressions.add(expression.toString());
        return this;
    }

    public QueryBuilder selectAllFrom(String tableName){
        StringBuilder expression = new StringBuilder(SELECT).append(SPACE).append(ALL).append(SPACE).append(FROM).append(SPACE).append(tableName);
        expressions.add(expression.toString());
        return this;
    }

    public QueryBuilder query(String query){
        expressions.add(query);
        return this;
    }


    public String build(){
        StringBuilder stringBuilder = new StringBuilder("");
        for (String expression :expressions) {
            stringBuilder.append(expression);
        }
        return stringBuilder.toString();
    }
}
