package com.richard.jo.sqlitebuilder;

import android.test.InstrumentationTestCase;

public class QueryBuilderTest extends InstrumentationTestCase {

    public void testWhereEqualInt() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder().whereEqual("column1", 1)
                .OR().whereEqual("column2", 2);

        assertEquals("column1=1 OR column2=2", queryBuilder.build());
    }

    public void testWhereEqualString() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder().whereEqual("column1", "1")
                .AND().whereEqual("column2", "2");

        assertEquals("column1='1' AND column2='2'", queryBuilder.build());
    }

    public void testWhereLike() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder().whereLike("column1", "1")
                .OR().whereLike("column2", "2");

        assertEquals("column1 LIKE '%1%' OR column2 LIKE '%2%'", queryBuilder.build());
    }

    public void testWhereNotLike() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder().whereNotLike("column1", "1");
        assertEquals("column1 NOT LIKE '%1%'", queryBuilder.build());
    }
}
