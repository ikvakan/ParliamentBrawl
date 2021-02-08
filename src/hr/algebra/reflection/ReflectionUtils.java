/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 *
 * @author IgorKvakan
 */
public class ReflectionUtils {

    public static void readClassInfo(Class<?> clazz, StringBuilder classInfo) {
        appendPackage(clazz, classInfo);
        appendModifiers(clazz, classInfo);
        appendParent(clazz, classInfo, true);
        appendInterfaces(clazz, classInfo);
    }

    private static void appendPackage(Class<?> clazz, StringBuilder classInfo) {
        classInfo
                .append("<h2> ")
                .append(clazz.getPackage())
                .append("</h2>")
                .append("\n\n")
                .append("<ul>")
                .append("\n");

    }

    private static void appendModifiers(Class<?> clazz, StringBuilder classInfo) {
        classInfo
                .append("<li>")
                .append(Modifier.toString(clazz.getModifiers()))
                .append(" ")
                .append(clazz.getSimpleName())
                .append("</li>");

    }

    private static void appendParent(Class<?> clazz, StringBuilder classInfo, boolean first) {
        Class<?> parent = clazz.getSuperclass();

        if (parent == null) {
            return;
        }
        if (first) {
            classInfo.append("\n<li>extends");

        }
        classInfo
                .append(" ")
                .append(parent.getName())
                .append("</li>");

        appendParent(parent, classInfo, false);
    }

    private static void appendInterfaces(Class<?> clazz, StringBuilder classInfo) {
        if (clazz.getInterfaces().length > 0) {
            classInfo.append("\n<li>implements");
        }
        for (Class<?> in : clazz.getInterfaces()) {
            classInfo.append(" ").append(in.getName()).append("</li>");
        }
    }

    public static void readClassAndMembersInof(Class<?> clazz, StringBuilder classAndMembersInfo) {
        readClassInfo(clazz, classAndMembersInfo);
        appendFields(clazz, classAndMembersInfo);
        appendMethods(clazz, classAndMembersInfo);
        appendConstructors(clazz, classAndMembersInfo);
    }

    private static void appendFields(Class<?> clazz, StringBuilder classAndMembersInfo) {
        Field[] fields = clazz.getDeclaredFields();
        classAndMembersInfo.
                append("\n\n");

        for (Field field : fields) {
            classAndMembersInfo
                    .append("<li>")
                    .append(field)
                    .append("</li>")
                    .append("\n");
        }
    }

    private static void appendMethods(Class<?> clazz, StringBuilder classAndMembersInfo) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            classAndMembersInfo
                    .append("\n");
            appendMethodAnnotations(method, classAndMembersInfo);
            classAndMembersInfo
                    .append("<li>")
                    .append(Modifier.toString(method.getModifiers()))
                    .append(" ")
                    .append(method.getReturnType())
                    .append(" ")
                    .append(method.getName());

            appendParameters(method, classAndMembersInfo);
            appendExceptions(method, classAndMembersInfo);
            classAndMembersInfo.append("</li>");
        }

        classAndMembersInfo.append("\n");
    }

    private static void appendMethodAnnotations(Executable executable, StringBuilder classAndMembersInfo) {
        for (Annotation annotation : executable.getAnnotations()) {
            classAndMembersInfo
                    .append(annotation)
                    .append("\n");
        }
    }

    private static void appendParameters(Executable executable, StringBuilder classAndMembersInfo) {
        classAndMembersInfo.append("(");
        for (Parameter parameter : executable.getParameters()) {
            classAndMembersInfo
                    .append(parameter)
                    .append(", ");
        }
        if (classAndMembersInfo.toString().endsWith(", ")) {
            classAndMembersInfo.delete(classAndMembersInfo.length() - 2, classAndMembersInfo.length());
        }
        classAndMembersInfo.append(") ");

    }

    private static void appendExceptions(Executable executable, StringBuilder classAndMembersInfo) {
        Class<?>[] exceptionTypes = executable.getExceptionTypes();
        if (exceptionTypes.length > 0) {
            classAndMembersInfo.append(" throws ");
            for (Class<?> exceptionType : exceptionTypes) {
                classAndMembersInfo
                        .append(exceptionType)
                        //.append(", ")
                        .append("</li>");
            }
            if (classAndMembersInfo.toString().endsWith(", ")) {
                classAndMembersInfo.delete(classAndMembersInfo.length() - 2, classAndMembersInfo.length());
            }
        }
    }

    private static void appendConstructors(Class<?> clazz, StringBuilder classAndMembersInfo) {
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {

            classAndMembersInfo.append("\n");
            
                    
            appendMethodAnnotations(constructor, classAndMembersInfo);
            classAndMembersInfo
                    .append("<li>")
                    .append(Modifier.toString(constructor.getModifiers()))
                    .append(" ")
                    .append(constructor.getName());
                    
            appendParameters(constructor, classAndMembersInfo);
            appendExceptions(constructor, classAndMembersInfo);
        }
    }

}
