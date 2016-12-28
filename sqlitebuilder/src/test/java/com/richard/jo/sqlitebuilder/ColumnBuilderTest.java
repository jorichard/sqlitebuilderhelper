package com.richard.jo.sqlitebuilder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColumnBuilderTest {


    @Test
    public void testColumnBuilder() throws Exception {
        ColumnBuilder columnBuilder = new ColumnBuilder("test_column", "TEXT");

        assertEquals("test_column TEXT", columnBuilder.execute());
    }

    @Test
    public void testPrimaryAutoIncrement() throws Exception {
        ColumnBuilder columnBuilder = new ColumnBuilder("_id", "INTEGER").setPrimaryKey().setAutoIncrement();

        assertEquals("_id INTEGER PRIMARY KEY AUTOINCREMENT", columnBuilder.execute());
    }
}
