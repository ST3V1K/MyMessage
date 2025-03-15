package me.kryz.mymessage.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Credits to ChatGPT xd
 */
public final class ReflectionUtils {
    private static final Map<String, Class<?>> CLASS_CACHE = new HashMap<>();
    private static final Map<String, Field> FIELD_CACHE = new HashMap<>();
    private static final Map<String, Method> METHOD_CACHE = new HashMap<>();

    /**
     * Obtiene una clase por su nombre, usando caché para evitar llamadas repetidas a Class.forName.
     *
     * @param className El nombre completo de la clase.
     * @return La clase correspondiente.
     * @throws RuntimeException Si la clase no se encuentra.
     */
    public static Class<?> getClass(final String className) {
        return CLASS_CACHE.computeIfAbsent(className, key -> {
            try {
                return Class.forName(key);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Clase no encontrada: " + key, e);
            }
        });
    }

    /**
     * Obtiene un campo de una clase por su nombre, usando caché para evitar llamadas repetidas a getDeclaredField.
     *
     * @param clazz      La clase que contiene el campo.
     * @param fieldName  El nombre del campo.
     * @return El campo correspondiente.
     * @throws RuntimeException Si el campo no se encuentra.
     */
    public static Field getField(final Class<?> clazz, final String fieldName) {
        final String cacheKey = clazz.getName() + "#" + fieldName;
        return FIELD_CACHE.computeIfAbsent(cacheKey, key -> {
            try {
                final Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
//                throw new RuntimeException("Campo no encontrado: " + fieldName + " en " + clazz.getName(), e);
            }
            return null;
        });
    }

    public static Field getFields(final Class<?> clazz, final String... fields){
        Field field = null;
        for (final String s : fields) {
            final var f = getField(clazz, s);
            if(f == null) continue;
            field = f;
        }
        return field;
    }

    /**
     * Obtiene un método de una clase por su nombre y tipos de parámetros, usando caché para evitar llamadas repetidas a getMethod.
     *
     * @param clazz          La clase que contiene el método.
     * @param methodName     El nombre del método.
     * @param parameterTypes Los tipos de los parámetros del método.
     * @return El método correspondiente.
     * @throws RuntimeException Si el método no se encuentra.
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        final String cacheKey = clazz.getName() + "#" + methodName;
        return METHOD_CACHE.computeIfAbsent(cacheKey, key -> {
            try {
                final Method method = clazz.getMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Método no encontrado: " + methodName + " en " + clazz.getName(), e);
            }
        });
    }

    /**
     * Obtiene el valor de un campo en un objeto.
     *
     * @param field  El campo.
     * @param object El objeto del cual obtener el valor.
     * @return El valor del campo.
     * @throws RuntimeException Si ocurre un error al obtener el valor.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(final Field field, final Object object) {
        try {
            return (T) field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al obtener el valor del campo: " + field.getName(), e);
        }
    }

    /**
     * Establece el valor de un campo en un objeto.
     *
     * @param field  El campo.
     * @param object El objeto en el cual establecer el valor.
     * @param value  El valor a establecer.
     * @throws RuntimeException Si ocurre un error al establecer el valor.
     */
    public static void setFieldValue(final Field field, final Object object, final Object value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al establecer el valor del campo: " + field.getName(), e);
        }
    }

    /**
     * Invoca un método en un objeto con los argumentos proporcionados.
     *
     * @param method     El método a invocar.
     * @param object     El objeto en el cual invocar el método.
     * @param arguments  Los argumentos para el método.
     * @return El resultado de la invocación del método.
     * @throws RuntimeException Si ocurre un error al invocar el método.
     */
    public static Object invokeMethod(final Method method, final Object object, final Object... arguments) {
        try {
            return method.invoke(object, arguments);
        } catch (Exception e) {
            throw new RuntimeException("Error al invocar el método: " + method.getName(), e);
        }
    }
}

