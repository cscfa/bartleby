package org.spleen.tool;

import java.lang.reflect.*;

/**
 * Sizeof tool class.
 * 
 * This class compile size of primitives
 * type to calculate an objet memory size.
 * 
 * In case of complex object, the result wouldn't
 * represent the reality.
 * 
 * @author Glen McCluskey & Associates LLC
 *
 */
public class Sizeof {

        private static final int SZ_REF = 4;

        /**
         * sizeof boolean.
         * 
         * Return the size of a boolean.
         * 
         * @param b 	the boolean.
         * @return 		the size of a primitive boolean.
         */
        public static int sizeof(boolean b)
        {
                return 1;
        }

        /**
         * sizeof byte.
         * 
         * Return the size of a byte.
         * 
         * @param b 	the byte.
         * @return 		the size of a primitive byte.
         */
        public static int sizeof(byte b)
        {
                return 1;
        }

        /**
         * sizeof char.
         * 
         * Return the size of a char.
         * 
         * @param c 	the char.
         * @return		the size of a primitive char.
         */
        public static int sizeof(char c)
        {
                return 2;
        }

        /**
         * sizeof short.
         * 
         * Return the size of a short.
         * 
         * @param s 	the short.
         * @return 		the size of a primitive short.
         */
        public static int sizeof(short s)
        {
                return 2;
        }

        /**
         * sizeof int.
         * 
         * Return the size of a int.
         * 
         * @param i 	the int.
         * @return 		the size of a primitive int.
         */
        public static int sizeof(int i)
        {
                return 4;
        }

        /**
         * sizeof long.
         * 
         * Return the size of a long.
         * 
         * @param l 	the long.
         * @return 		the size of a primitive long.
         */
        public static int sizeof(long l)
        {
                return 8;
        }

        /**
         * sizeof float.
         * 
         * Return the size of a float.
         * 
         * @param f 	the float.
         * @return 		the size of a primitive float.
         */
        public static int sizeof(float f)
        {
                return 4;
        }

        /**
         * sizeof double.
         * 
         * Return the size of a double.
         * 
         * @param d 	the double.
         * @return 		the size of a primitive double.
         */
        public static int sizeof(double d)
        {
                return 8;
        }

        /**
         * sizeof Class.
         * 
         * Return the size of a Class by calculating
         * it fields size.
         * 
         * @param c 	the Class.
         * @return 		the size of a Class.
         */
        private static int size_inst(Class<?> c)
        {
                Field flds[] = c.getDeclaredFields();
                int sz = 0;

                for (int i = 0; i < flds.length; i++) {
                        Field f = flds[i];
                        if (!c.isInterface() &&
                            (f.getModifiers() & Modifier.STATIC) != 0)
                                continue;
                        sz += size_prim(f.getType());
                }

                if (c.getSuperclass() != null)
                        sz += size_inst(c.getSuperclass());

                Class<?> cv[] = c.getInterfaces();
                for (int i = 0; i < cv.length; i++)
                        sz += size_inst(cv[i]);

                return sz;
        }

        /**
         * sizeof Class primitive.
         * 
         * Return the size of a primitive Class.
         * 
         * @param t 	the primitive Class.
         * @return 		the size of a primitive Class.
         */
        private static int size_prim(Class<?> t)
        { 
                if (t == Boolean.TYPE)
                        return 1;
                else if (t == Byte.TYPE)
                        return 1;
                else if (t == Character.TYPE)
                        return 2;
                else if (t == Short.TYPE)
                        return 2;
                else if (t == Integer.TYPE)
                        return 4;
                else if (t == Long.TYPE)
                        return 8;
                else if (t == Float.TYPE)
                        return 4;
                else if (t == Double.TYPE)
                        return 8;
                else if (t == Void.TYPE)
                        return 0;
                else
                        return SZ_REF;
        }
        
        /**
         * sizeof array.
         * 
         * Return the size of an array.
         * 
         * @param obj 	The array
         * @param c		The array class.
         * @return		the size of an array.
         */
        private static int size_arr(Object obj, Class<?> c)
        {
                Class<?> ct = c.getComponentType();
                int len = Array.getLength(obj);

                if (ct.isPrimitive()) {
                        return len * size_prim(ct);
                }
                else {
                        int sz = 0;
                        for (int i = 0; i < len; i++) {
                                sz += SZ_REF;
                                Object obj2 = Array.get(obj, i);
                                if (obj2 == null)
                                        continue;
                                Class<?> c2 = obj2.getClass();
                                if (!c2.isArray())
                                        continue;
                                sz += size_arr(obj2, c2);
                        }
                        return sz;
                }
        }

        /**
         * sizeof Object.
         * 
         * Return the size of an Object.
         * 
         * @param obj 	the Object.
         * @return 		the size of an Object.
         */
        public static int sizeof(Object obj)
        {
                if (obj == null)
                        return 0;

                Class<?> c = obj.getClass();

                if (c.isArray())
                        return size_arr(obj, c);
                else
                        return size_inst(c);
        }

}
