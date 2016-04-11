package com.example.venkatagovardhan.docorganizer;

/**
 * Created by Venkata Govardhan on 4/10/2016.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

public class SerializationUtils {
    private SerializationUtils() {
    }

    public static byte[] serialize(Object object) throws IOException, NotSerializableException {
        if(!(object instanceof Serializable)) {
            throw new NotSerializableException();
        } else {
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOut = null;

            byte[] var4;
            try {
                objectOut = new ObjectOutputStream(byteOutStream);
                objectOut.writeObject(object);
                var4 = byteOutStream.toByteArray();
            } finally {
                byteOutStream.close();
                if(objectOut != null) {
                    objectOut.close();
                }

            }

            return var4;
        }
    }

    public static Object deserialize(byte[] bytes) throws StreamCorruptedException, IOException, ClassNotFoundException {
        ByteArrayInputStream inStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectIn = null;

        Object var4;
        try {
            objectIn = new ObjectInputStream(inStream);
            var4 = objectIn.readObject();
        } finally {
            inStream.close();
            if(objectIn != null) {
                objectIn.close();
            }

        }

        return var4;
    }
}

