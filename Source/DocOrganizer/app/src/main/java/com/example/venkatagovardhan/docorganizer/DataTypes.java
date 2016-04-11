package com.example.venkatagovardhan.docorganizer;

/**
 * Created by Venkata Govardhan on 4/10/2016.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



public class DataTypes {
    public static final int TYPE_NA = -1;
    public static final int TYPE_OTHER = 100;
    public static final int TYPE_STRING = 101;
    public static final int TYPE_COLLECTION = 102;
    public static final int TYPE_SERIALIZABLE = 103;
    public static final int TYPE_INT = 201;
    public static final int TYPE_LONG = 202;
    public static final int TYPE_SHORT = 203;
    public static final int TYPE_BYTE = 204;
    public static final int TYPE_CHAR = 205;
    public static final int TYPE_BOOL = 206;
    public static final int TYPE_FLOAT = 306;
    public static final int TYPE_DOUBLE = 307;
    public static final String DATA_TYPE_TEXT = "TEXT";
    public static final String DATA_TYPE_INTEGER = "INTEGER";
    public static final String DATA_TYPE_NUMERIC = "NUMERIC";
    public static final String DATA_TYPE_REAL = "REAL";
    public static final String DATA_TYPE_VARCHAR = "TEXT";
    public static final String DATA_TYPE_DOUBLE = "REAL";
    public static final String DATA_TYPE_FLOAT = "REAL";
    public static final String DATA_TYPE_BOOLEAN = "NUMERIC";
    public static final String DATA_TYPE_NONE = "NONE";
    public static final String DATA_TYPE_BLOB = "NONE";

    public DataTypes() {
    }

    public static String getDataType(int fieldType) {
        switch(fieldType) {
            case 101:
                return "TEXT";
            case 201:
            case 202:
            case 203:
            case 204:
            case 205:
                return "INTEGER";
            case 206:
                return "NUMERIC";
            case 306:
            case 307:
                return "REAL";
            default:
                return "NONE";
        }
    }

    public static int getFieldType(Class<?> cls) {
        return cls.isAssignableFrom(String.class)?101:(!Integer.class.isAssignableFrom(cls) && !Integer.TYPE.isAssignableFrom(cls)?(!Long.class.isAssignableFrom(cls) && !Long.TYPE.isAssignableFrom(cls)?(!Double.class.isAssignableFrom(cls) && !Double.TYPE.isAssignableFrom(cls)?(!Float.class.isAssignableFrom(cls) && !Float.TYPE.isAssignableFrom(cls)?(!cls.isAssignableFrom(Character.class) && !cls.isAssignableFrom(Character.TYPE)?(!cls.isAssignableFrom(Byte.class) && !cls.isAssignableFrom(Byte.TYPE)?(!cls.isAssignableFrom(Short.class) && !cls.isAssignableFrom(Short.TYPE)?(!cls.isAssignableFrom(Boolean.class) && !cls.isAssignableFrom(Boolean.TYPE)?103:206):203):204):205):306):307):202):201);
    }
}

