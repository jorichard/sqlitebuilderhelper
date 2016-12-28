package com.richard.jo.sqlitebuilder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TableBuilderTest {

    @Test
    public void testTableBuilder() throws Exception {
        TableBuilder tableBuilder = new TableBuilder("test_table")
                .addAutoIncrementIntegerPrimaryKeyColumn("_id")
                .addLongColumn("time")
                .addIntegerColumn("type")
                .addTextColumn("detail")
                .addRealColumn("value");

        assertEquals("CREATE TABLE test_table(_id INTEGER PRIMARY KEY AUTOINCREMENT, time LONG, type INTEGER, detail TEXT, value REAL)", tableBuilder.execute());
    }

    @Test
    public void testAddUniqueConstraint() throws Exception {
        TableBuilder tableBuilder = new TableBuilder("test_table")
                .addTextColumn("text_column")
                .addUniqueConstraint("text_column", TableBuilder.OnConflictClause.REPLACE);
        assertEquals("CREATE TABLE test_table(text_column TEXT, UNIQUE(text_column) ON CONFLICT REPLACE)", tableBuilder.execute());
    }

    @Test
    public void testAddUniqueConstraintMultipleColumns() throws Exception {
        String columnName1 = "test_column1";
        String columnName2 = "test_column2";

        List<String> columnNames = new ArrayList<>();
        columnNames.add(columnName1);
        columnNames.add(columnName2);

        TableBuilder tableBuilder = new TableBuilder("test_table")
                .addTextColumn(columnName1)
                .addTextColumn(columnName2)
                .addUniqueConstraint(columnNames, TableBuilder.OnConflictClause.REPLACE);
        assertEquals("CREATE TABLE test_table(test_column1 TEXT, test_column2 TEXT, UNIQUE(test_column1, test_column2) ON CONFLICT REPLACE)", tableBuilder.execute());
    }

    @Test
    public void testAddForeignKeyWithDeleteCascade() throws Exception {
        String columnName = "test_column";
        String foreignTableName = "foreign_table";
        String foreignColumnName = "foreign_column";

        TableBuilder tableBuilder = new TableBuilder("test_table");
        tableBuilder.addForeignKey(columnName, foreignTableName, foreignColumnName, true);

        assertEquals("CREATE TABLE test_table(FOREIGN KEY(test_column) REFERENCES foreign_table(foreign_column) ON DELETE CASCADE)", tableBuilder.execute());
    }

    @Test
    public void testAddForeignKeyWithoutDeleteCascade() throws Exception {
        String columnName = "test_column";
        String foreignTableName = "foreign_table";
        String foreignColumnName = "foreign_column";

        TableBuilder tableBuilder = new TableBuilder("test_table");
        tableBuilder.addForeignKey(columnName, foreignTableName, foreignColumnName, false);

        assertEquals("CREATE TABLE test_table(FOREIGN KEY(test_column) REFERENCES foreign_table(foreign_column))", tableBuilder.execute());
    }
}
