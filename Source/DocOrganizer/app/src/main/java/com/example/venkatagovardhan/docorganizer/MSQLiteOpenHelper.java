package com.example.venkatagovardhan.docorganizer;

/**
 * Created by Venkata Govardhan on 4/10/2016.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.michaldabski.msqlite.models.Table;
import com.michaldabski.msqlite.queries.CreateTable;
import com.michaldabski.msqlite.queries.Drop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public abstract class MSQLiteOpenHelper extends SQLiteOpenHelper {
    protected final Collection<Class<?>> classes;

    @TargetApi(11)
    public MSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.classes = new HashSet();
    }

    public MSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.classes = new HashSet();
    }

    public MSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, Collection<Class<?>> trackedClasses) {
        this(context, name, factory, version);
        this.trackClasses(trackedClasses);
    }

    public MSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, Class<?>[] trackedClasses) {
        this(context, name, factory, version);
        this.trackClasses(trackedClasses);
    }

    public static void createTable(SQLiteDatabase database, Class<?> type, boolean ifNotExist) {
        createTable(database, new Table(type), ifNotExist);
    }

    public static void createTable(SQLiteDatabase database, Table table, boolean ifNotExist) {
        database.execSQL((new CreateTable(table)).setIF_NOT_EXIST(ifNotExist).build());
    }

    private static void upgradeTable(SQLiteDatabase database, Table table) {
        Cursor cursor = database.rawQuery("PRAGMA table_info(" + table.getName() + ");", (String[])null);
        if(cursor.getCount() == 0) {
            createTable(database, table, false);
            Log.i("DatabaseUpgrade", "table created: " + table.getName());
        } else {
            Table currentDatabaseTable = Table.fromCursor(table.getName(), cursor);
            Iterator var5 = Table.upgradeTable(currentDatabaseTable, table).iterator();

            while(var5.hasNext()) {
                String sql = (String)var5.next();
                database.execSQL(sql);
                Log.i("DatabaseUpgrade", "table altered. Query: " + sql);
            }
        }

    }

    public void upgradeDatabase() {
        SQLiteDatabase database = this.getWritableDatabase();
        this.upgradeDatabase(database);
        database.close();
    }

    public void upgradeDatabase(SQLiteDatabase database) {
        Iterator var3 = this.classes.iterator();

        while(var3.hasNext()) {
            Class c = (Class)var3.next();
            upgradeTable(database, new Table(c));
        }

    }

    public void trackClasses(Collection<Class<?>> classes) {
        this.classes.addAll(classes);
    }

    public void trackClass(Class<?> trackedClass) {
        this.classes.add(trackedClass);
    }

    public void trackClasses(Class<?>[] classes) {
        Class[] var5 = classes;
        int var4 = classes.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Class c = var5[var3];
            this.trackClass(c);
        }

    }

    public void onCreate(SQLiteDatabase db) {
        Iterator var3 = this.classes.iterator();

        while(var3.hasNext()) {
            Class c = (Class)var3.next();
            createTable(db, c, true);
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.upgradeDatabase(db);
    }

    public static void dropTable(SQLiteDatabase database, Class<?> type) {
        database.execSQL((new Drop(type)).build());
    }

    public static void dropTable(SQLiteDatabase database, Class<?> type, boolean ifExists) {
        database.execSQL((new Drop(type)).setIfExists(ifExists).build());
    }

    public static void deleteFrom(SQLiteDatabase database, Class<?> type, String whereClause, String[] whereArgs) {
        database.delete((new Table(type)).getName(), whereClause, whereArgs);
    }

    private static int delete(SQLiteDatabase database, Table table, String whereClause, String[] whereArgs) {
        return database.delete(table.getName(), whereClause, whereArgs);
    }

    private static int delete(SQLiteDatabase database, Table table, Object item) {
        String whereClause;
        String[] whereArgs;
        if(table.getPrimaryKeys().isEmpty()) {
            whereClause = table.getFullWhereClause();
            whereArgs = table.getFullWhereArgs(item);
        } else {
            whereClause = table.getPrimaryWhereClause();
            whereArgs = table.getPrimaryWhereArgs(item);
        }

        return delete(database, table, whereClause, whereArgs);
    }

    public static int delete(SQLiteDatabase database, Object item) {
        Table table = new Table(item.getClass());
        return delete(database, table, item);
    }

    public static <T> int delete(SQLiteDatabase database, Class<T> type, Collection<T> items) {
        Table table = new Table(type);
        int n = 0;

        Object item;
        for(Iterator var6 = items.iterator(); var6.hasNext(); n += delete(database, table, item)) {
            item = (Object)var6.next();
        }

        return n;
    }

    public <T> int delete(Class<T> type, Collection<T> items) {
        SQLiteDatabase database = this.getWritableDatabase();
        int result = delete(database, type, items);
        database.close();
        return result;
    }

    public int delete(Object item) {
        SQLiteDatabase database = this.getWritableDatabase();
        int result = delete(database, item);
        database.close();
        return result;
    }

    public static <T> List<T> select(SQLiteDatabase database, Class<T> type, String selection, String[] selectionArgs, String orderBy, String limit) {
        return select(database, type, (String[])null, selection, selectionArgs, orderBy, limit);
    }

    public static <T> List<T> select(SQLiteDatabase database, Class<T> type, String[] columns, String selection, String[] selectionArgs, String orderBy, String limit) {
        ArrayList result = new ArrayList();
        Table table = new Table(type);
        Cursor cursor = database.query(table.getName(), columns, selection, selectionArgs, (String)null, (String)null, orderBy, limit);

        while(cursor.moveToNext()) {
            result.add(table.getRow(cursor, type));
        }

        cursor.close();
        return result;
    }

    public static <T> Object selectFirst(SQLiteDatabase database, Class<T> type, String selection, String[] selectionArgs, String orderBy) {
        Table table = new Table(type);
        Cursor cursor = database.query(table.getName(), (String[])null, selection, selectionArgs, (String)null, (String)null, orderBy, "1");
        Object result;
        if(cursor.moveToFirst()) {
            result = table.getRow(cursor, type);
        } else {
            result = null;
        }

        cursor.close();
        return result;
    }

    public static <T> void selectForeach(SQLiteDatabase database, Class<T> type, MSQLiteOpenHelper.OnRowSelectedListener<T> onRowSelectedListener, String selection, String[] selectionArgs, String orderBy, String limit) {
        Table table = new Table(type);
        Cursor cursor = database.query(table.getName(), (String[])null, selection, selectionArgs, (String)null, (String)null, orderBy, limit);

        while(cursor.moveToNext()) {
            onRowSelectedListener.onRowSelected(cursor, table.getRow(cursor, type));
        }

        cursor.close();
    }

    public static <T> void insert(SQLiteDatabase database, Class<T> typeOfItem, Collection<T> items) {
        Table table = new Table(typeOfItem);
        Iterator var5 = items.iterator();

        while(var5.hasNext()) {
            Object row = var5.next();
            insert(database, table, row);
        }

    }

    public static int update(SQLiteDatabase database, Object object) {
        Table table = new Table(object.getClass());
        return update(database, table, object, table.getPrimaryWhereClause(), table.getPrimaryWhereArgs(object));
    }

    private static int update(SQLiteDatabase database, Table table, Object object, String whereClause, String[] whereArgs) {
        database.update(table.getName(), table.getContentValues(object), whereClause, whereArgs);
        return update(database, table, table.getContentValues(object), whereClause, whereArgs);
    }

    private static int update(SQLiteDatabase database, Table table, Object object, Collection<String> columns, String whereClause, String[] whereArgs) {
        return update(database, table, table.getContentValues(object, columns), whereClause, whereArgs);
    }

    public static int update(SQLiteDatabase database, Object object, Collection<String> columns, String whereClause, String[] whereArgs) {
        Table table = new Table(object.getClass());
        return update(database, table, object, columns, table.getPrimaryWhereClause(), table.getPrimaryWhereArgs(object));
    }

    private static int update(SQLiteDatabase database, Table table, ContentValues contentValues, String whereClause, String[] whereArgs) {
        return database.update(table.getName(), contentValues, whereClause, whereArgs);
    }

    public static <T> int update(SQLiteDatabase database, Class<T> type, Collection<T> objects) {
        Table table = new Table(type);
        String whereClause = table.getPrimaryWhereClause();
        int affectedRows = 0;

        Object object;
        for(Iterator var7 = objects.iterator(); var7.hasNext(); affectedRows += update(database, table, object, whereClause, table.getPrimaryWhereArgs(object))) {
            object = (Object)var7.next();
        }

        return affectedRows;
    }

    public int update(Object object) {
        SQLiteDatabase database = this.getWritableDatabase();
        int affectedRows = update(database, object);
        database.close();
        return affectedRows;
    }

    public <T> int update(Class<T> type, Collection<T> objects) {
        SQLiteDatabase database = this.getWritableDatabase();
        int affectedRows = update(database, type, objects);
        database.close();
        return affectedRows;
    }

    public <T> List<T> select(Class<T> type, String selection, String[] selectionArgs, String orderBy, String limit) {
        SQLiteDatabase database = this.getReadableDatabase();
        List result = select(database, type, selection, selectionArgs, orderBy, limit);
        database.close();
        return result;
    }

    public <T> List<T> selectAll(Class<T> type) {
        return this.select(type, (String)null, (String[])null, (String)null, (String)null);
    }

    public static long insert(SQLiteDatabase database, Object item) {
        return insert(database, new Table(item.getClass()), item);
    }

    private static long insert(SQLiteDatabase database, Table table, Object item) {
        long id = database.insert(table.getName(), (String)null, table.getContentValues(item));
        table.setRowID(item, id);
        return id;
    }

    public long insert(Object item) {
        SQLiteDatabase database = this.getWritableDatabase();
        long insertId = insert(database, item);
        database.close();
        return insertId;
    }

    public <T> void insert(Class<T> classOfItem, Collection<T> items) {
        SQLiteDatabase database = this.getWritableDatabase();
        insert(database, classOfItem, items);
        database.close();
    }

    private static long replace(SQLiteDatabase database, Table table, Object item) {
        long id = database.replace(table.getName(), (String)null, table.getContentValues(item));
        table.setRowID(item, id);
        return id;
    }

    public static long replace(SQLiteDatabase database, Object item) {
        return replace(database, new Table(item.getClass()), item);
    }

    public static <T> void replace(SQLiteDatabase database, Class<T> type, Collection<T> items) {
        Table table = new Table(type);
        Iterator var5 = items.iterator();

        while(var5.hasNext()) {
            Object item = (Object)var5.next();
            replace(database, table, item);
        }

    }

    public <T> void replace(Class<T> type, Collection<T> items) {
        SQLiteDatabase database = this.getWritableDatabase();
        replace(database, type, items);
        database.close();
    }

    public long replace(Object item) {
        SQLiteDatabase database = this.getWritableDatabase();
        long id = replace(database, item);
        database.close();
        return id;
    }

    public void createTable(Class<?> type, boolean ifNotExist) {
        SQLiteDatabase database = this.getWritableDatabase();
        createTable(database, type, ifNotExist);
        database.close();
    }

    public interface OnRowSelectedListener<T> {
        void onRowSelected(Cursor var1, T var2);
    }
}
