package com.example.venkatagovardhan.docorganizer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Venkata Govardhan on 4/10/2016.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class Annotations {
    public Annotations() {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ColumnName {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface DataType {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Default {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface NotNull {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface PrimaryKey {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TableName {
        String value();
    }
}
