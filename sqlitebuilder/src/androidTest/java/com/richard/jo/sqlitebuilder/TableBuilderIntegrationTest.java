package com.richard.jo.sqlitebuilder;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.test.InstrumentationTestCase;
import android.test.IsolatedContext;

import java.util.Arrays;

public class TableBuilderIntegrationTest extends InstrumentationTestCase {

    private static final String TEST_TABLE_NAME = "test_table";
    private static final String TEST_ID = "_id";
    private static final String TEST_LONG = "test_long";
    private static final String TEST_INT = "test_int";
    private static final String TEST_TEXT = "test_text";
    private static final String TEST_REAL = "test_real";

    private IsolatedContext isolatedContext;
    TableBuilder tableBuilder;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        isolatedContext = new IsolatedContext(null, getInstrumentation().getContext());

        tableBuilder = new TableBuilder(TEST_TABLE_NAME)
                .addAutoIncrementIntegerPrimaryKeyColumn(TEST_ID)
                .addLongColumn(TEST_LONG)
                .addIntegerColumn(TEST_INT)
                .addTextColumn(TEST_TEXT)
                .addRealColumn(TEST_REAL);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        getInstrumentation().getContext().deleteDatabase(SQLiteBuilderTestDatabase.getDbName());
    }

    public void testTableBuilder() throws Exception {
        SQLiteBuilderTestDatabase sqliteBuilderTestDatabase = new SQLiteBuilderTestDatabase(isolatedContext, Arrays.asList(tableBuilder));
        SQLiteDatabase sqliteDatabase = sqliteBuilderTestDatabase.getWritableDatabase();

        long longValue = 2;
        int intValue = 5;
        String textValue = "text";
        double doubleValue = 3.14159;

        ContentValues contentValues = new ContentValues();
        contentValues.put(TEST_LONG, longValue);
        contentValues.put(TEST_INT, intValue);
        contentValues.put(TEST_TEXT, textValue);
        contentValues.put(TEST_REAL, doubleValue);

        long rowId = sqliteDatabase.insert(TEST_TABLE_NAME, null, contentValues);
        assertTrue(rowId > -1);

        Cursor cursor = sqliteDatabase.query(TEST_TABLE_NAME, null, TEST_ID + "=" + rowId, null, null, null, null, null);
        cursor.moveToNext();

        assertEquals(rowId, cursor.getInt(cursor.getColumnIndex(TEST_ID)));
        assertEquals(longValue, cursor.getLong(cursor.getColumnIndex(TEST_LONG)));
        assertEquals(intValue, cursor.getInt(cursor.getColumnIndex(TEST_INT)));
        assertEquals(textValue, cursor.getString(cursor.getColumnIndex(TEST_TEXT)));
        assertEquals(doubleValue, cursor.getDouble(cursor.getColumnIndex(TEST_REAL)));

        cursor.close();
    }

    public void testUniqueConstraint() throws Exception {
        tableBuilder.addUniqueConstraint(TEST_INT, TableBuilder.OnConflictClause.ABORT);
        SQLiteBuilderTestDatabase sqliteBuilderTestDatabase = new SQLiteBuilderTestDatabase(isolatedContext, Arrays.asList(tableBuilder));
        SQLiteDatabase sqliteDatabase = sqliteBuilderTestDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TEST_LONG, 1);
        contentValues.put(TEST_INT, 2);
        contentValues.put(TEST_TEXT, "test");
        contentValues.put(TEST_REAL, 3.4);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(TEST_LONG, 34);
        contentValues2.put(TEST_INT, 2);
        contentValues2.put(TEST_TEXT, "test2");
        contentValues2.put(TEST_REAL, 6.42);

        long rowId = sqliteDatabase.insert(TEST_TABLE_NAME, null, contentValues);
        assertTrue(rowId > -1);

        SQLiteConstraintException sqLiteConstraintException = null;
        try{
            sqliteDatabase.insertOrThrow(TEST_TABLE_NAME, null, contentValues2);
        }catch (SQLiteConstraintException exception){
            sqLiteConstraintException = exception;
        }

        assertNotNull(sqLiteConstraintException);
    }

    public void testForeignKeyDeleteCascade() throws Exception {
        // arrange
        String table2Name = "test_table2";
        String table2ColumnName = "int_column";

        tableBuilder.addUniqueConstraint(TEST_INT, TableBuilder.OnConflictClause.ABORT);

        TableBuilder tableBuilder2 = new TableBuilder(table2Name)
                .addIntegerColumn(table2ColumnName)
                .addForeignKey(table2ColumnName, TEST_TABLE_NAME, TEST_INT, true);

        SQLiteBuilderTestDatabase sqliteBuilderTestDatabase = new SQLiteBuilderTestDatabase(isolatedContext, Arrays.asList(tableBuilder, tableBuilder2));
        SQLiteDatabase sqliteDatabase = sqliteBuilderTestDatabase.getWritableDatabase();

        int intValue = 5;
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEST_LONG, 1);
        contentValues.put(TEST_INT, intValue);
        contentValues.put(TEST_TEXT, "test");
        contentValues.put(TEST_REAL, 3.4);
        sqliteDatabase.insertOrThrow(TEST_TABLE_NAME, null, contentValues);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(table2ColumnName, intValue);
        sqliteDatabase.insertOrThrow(table2Name, null, contentValues2);

        // act
        sqliteDatabase.delete(TEST_TABLE_NAME, null, null);

        Cursor cursor = sqliteDatabase.query(TEST_TABLE_NAME, null, null, null, null, null, null, null);
        Cursor foreignCursor = sqliteDatabase.query(table2Name, null, null, null, null, null, null, null);

        // assert
        assertFalse(cursor.moveToNext());
        assertFalse(foreignCursor.moveToNext());

        foreignCursor.close();
        cursor.close();
    }
}
