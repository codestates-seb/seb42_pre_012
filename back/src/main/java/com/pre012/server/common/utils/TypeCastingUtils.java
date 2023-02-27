package com.pre012.server.common.utils;

import java.util.ArrayList;
import java.util.List;

public class TypeCastingUtils {
    public static <T> List<T> objToList(Object obj, Class<T> classType) {
        List<T> list = new ArrayList<>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                list.add(classType.cast(o));
            }
            return list;
        }
        return null;
    }
}
