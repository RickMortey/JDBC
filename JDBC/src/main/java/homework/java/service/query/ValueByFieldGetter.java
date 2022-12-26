package homework.java.service.query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.beans.*;
import org.apache.commons.beanutils.BeanUtils;

public class ValueByFieldGetter{
    private static Logger log = Logger.getLogger(BeanUtils.class
            .getSimpleName());//from w  w  w.  j ava  2  s  . c  om
    public static Object getFieldValueWithGetter(Object obj, Field field) {
        try {
            Method m = getGetterMethodForField(obj, field);
            m.setAccessible(true);
            return m.invoke(obj);
        } catch (NoSuchMethodException e) {
            log.log(Level.INFO, "No getter for field: " + field.getName()
                    + ", using field it's self", e);
            try {
                field.setAccessible(true);
                return field.get(obj);
            } catch (Throwable iae) {
                log.log(Level.SEVERE,
                        "Unable to access field even after enabling access.",
                        iae);
            }
        } catch (InvocationTargetException e) {
            log.log(Level.SEVERE,
                    "Unable to invoke getter on supplied object: " + obj
                            + ", field: " + field.getName(), e);
        } catch (IllegalAccessException e) {
            log.log(Level.SEVERE,
                    "Access not allowed on explicitly marked accessible field through reflection.",
                    e);
        }
        return null;
    }
    public static Object getFieldValueWithGetter(Object obj,
                                                 String fieldName) {
        try {
            return getFieldValueWithGetter(obj, obj.getClass()
                    .getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            log.log(Level.SEVERE, "Unable to find field on object", e);
            return null;
        }
    }
    public static Method getGetterMethodForField(Object obj, Field field)
            throws NoSuchMethodException {
        try {
            return obj.getClass().getDeclaredMethod(
                    getGetterMethodNameForField(obj, field));
        } catch (NoSuchMethodException nsme) {
            log.log(Level.WARNING, "Can't find method.", nsme);
            throw nsme;
        }
    }
    public static Method getGetterMethodForField(Object obj,
                                                 String fieldName) throws NoSuchMethodException,
            NoSuchFieldException {
        return obj.getClass().getDeclaredMethod(
                getGetterMethodNameForField(fieldName, obj.getClass()
                        .getDeclaredField(fieldName)));
    }
    public static Method getGetterMethodForField(Class obj, String fieldName)
            throws NoSuchMethodException, NoSuchFieldException {
        return obj.getDeclaredMethod(getGetterMethodNameForField(fieldName,
                obj.getDeclaredField(fieldName)));
    }
    public static String getGetterMethodNameForField(Object obj, Field field) {
        if (field.getType() == Boolean.class
                || field.getType() == boolean.class) {
            String isGetterName = "is"
                    + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1);
            try {
                obj.getClass().getDeclaredMethod(isGetterName);
                return isGetterName;
            } catch (NoSuchMethodException nsme) {
            }
        }
        return "get" + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
    }
}