/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.generator.util;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import pan.glenn.codegen.context.DomainField;
import pan.glenn.codegen.context.GlobalConfig;
import pan.glenn.codegen.generator.GlennJavaFormatter;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class JavaBeanUtil {

    public static Method getJavaBeansGetter(DomainField domainField) {
        FullyQualifiedJavaType fieldJavaType = new FullyQualifiedJavaType(domainField.getFieldJavaType());
        String property = domainField.getFieldName();

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(fieldJavaType);
        method.setName(JavaBeansUtil.getGetterMethodName(property, fieldJavaType));
        GlennCommentGenerator.addGetterComment(method, domainField);

        StringBuilder sb = new StringBuilder();
        sb.append("return "); //$NON-NLS-1$
        sb.append(property);
        sb.append(';');
        method.addBodyLine(sb.toString());

        return method;
    }

    public static Field getJavaBeansField(DomainField domainField) {
        FullyQualifiedJavaType fieldJavaType = new FullyQualifiedJavaType(domainField.getFieldJavaType());
        String property = domainField.getFieldName();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(fieldJavaType);
        field.setName(property);
        return field;
    }

    public static Method getJavaBeansSetter(DomainField domainField) {
        FullyQualifiedJavaType fieldJavaType = new FullyQualifiedJavaType(domainField.getFieldJavaType());
        String property = domainField.getFieldName();

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(JavaBeansUtil.getSetterMethodName(property));
        method.addParameter(new Parameter(fieldJavaType, property));
        GlennCommentGenerator.addSetterComment(method, domainField);

        StringBuilder sb = new StringBuilder();
        if (GlobalConfig.getString("isTrimStringsEnabled") != null
                && "true".equals(GlobalConfig.getString("isTrimStringsEnabled"))
                && domainField.isStringColumn()) {
            sb.append("this."); //$NON-NLS-1$
            sb.append(property);
            sb.append(" = "); //$NON-NLS-1$
            sb.append(property);
            sb.append(" == null ? null : "); //$NON-NLS-1$
            sb.append(property);
            sb.append(".trim();"); //$NON-NLS-1$
            method.addBodyLine(sb.toString());
        } else {
            sb.append("this."); //$NON-NLS-1$
            sb.append(property);
            sb.append(" = "); //$NON-NLS-1$
            sb.append(property);
            sb.append(';');
            method.addBodyLine(sb.toString());
        }

        return method;
    }

    public static final Field getField(String fieldName,
                                       FullyQualifiedJavaType javaType,
                                       JavaVisibility visibility) {
        Field field = new Field(fieldName, javaType);
        field.setVisibility(visibility);
        return field;
    }

    public static final Field getField(String fieldName,
                                       String javaType, JavaVisibility visibility) {
        return getField(fieldName, new FullyQualifiedJavaType(javaType), visibility);
    }

    public static final Method getMethod(String name,
            String returnType, JavaVisibility visibility) {
        Method method = new Method(name);
        method.setVisibility(visibility);
        method.setReturnType(new FullyQualifiedJavaType(returnType));
        return method;
    }

    public static final Method getMethod(String name,
            FullyQualifiedJavaType returnType, JavaVisibility visibility) {
        Method method = new Method(name);
        method.setVisibility(visibility);
        method.setReturnType(returnType);
        return method;
    }

    public static  GeneratedJavaFile getGeneratedJavaFile(CompilationUnit compilationUnit) {
        return  new GeneratedJavaFile(compilationUnit,
                GlobalConfig.getString("src.path"),
                GlobalConfig.getString("java.file.encoding"),
                new GlennJavaFormatter());
    }

    public static final FullyQualifiedJavaType getFullType(String fullyQualifiedJavaType) {
        return new FullyQualifiedJavaType(fullyQualifiedJavaType);
    }

    public static final String getFullClassName(String packageName, String className) {
        if (packageName == null || packageName.trim().length() == 0) {
            return className;
        }
        packageName = packageName.trim();
        if (packageName.endsWith(".")) {
            return packageName + className;
        }
        return packageName + "." + className;
    }
}
