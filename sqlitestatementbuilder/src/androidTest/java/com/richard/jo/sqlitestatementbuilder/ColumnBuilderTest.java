package com.richard.jo.sqlitestatementbuilder;

import android.test.InstrumentationTestCase;

public class ColumnBuilderTest extends InstrumentationTestCase {

    public void testColumnBuilder() throws Exception {
        ColumnBuilder columnBuilder = new ColumnBuilder("test_column", "TEXT");

        assertEquals("test_column TEXT", columnBuilder.execute());
    }

    public void testPrimaryAutoIncrement() throws Exception {
        ColumnBuilder columnBuilder = new ColumnBuilder("_id", "INTEGER").setPrimaryKey().setAutoIncrement();

        assertEquals("_id INTEGER PRIMARY KEY AUTOINCREMENT", columnBuilder.execute());
    }
}
