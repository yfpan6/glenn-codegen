/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.*;
import pan.glenn.codegen.context.DomainConfig;
import pan.glenn.codegen.context.DomainField;
import pan.glenn.codegen.context.GlobalConfig;
import pan.glenn.codegen.generator.util.GlennCommentGenerator;
import pan.glenn.codegen.generator.util.JavaBeanUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class EntityGenerator extends GlennGenerator{

    public EntityGenerator(DomainConfig domainConfig) {
        super(domainConfig);
    }

    public List<GeneratedJavaFile> generate() {
        String classFullName = JavaBeanUtil.getFullClassName(
                GlobalConfig.getString("entity.pkg"),
                domainConfig.getEntityClassENName());
        TopLevelClass clazz = new TopLevelClass(classFullName);
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.setSuperClass(GlobalConfig.getString("base.entity"));
        clazz.addImportedType(GlobalConfig.getString("base.entity"));

        GlennCommentGenerator.addClassComment(clazz, domainConfig);

        List<DomainField> fields = domainConfig.getFields();
        Field colNameField = null;
        for (DomainField domainField : fields) {
            colNameField = new Field();
            colNameField.setType(FullyQualifiedJavaType.getStringInstance());
            colNameField.setVisibility(JavaVisibility.PUBLIC);
            colNameField.setStatic(true);
            colNameField.setFinal(true);
            colNameField.setName(domainField.getColumnName().toUpperCase());
            colNameField.setInitializationString("\"" + domainField.getColumnName() + "\"");
            clazz.addField(colNameField);
        }


        Field field = null;
        Method getter = null;
        Method setter = null;
        for (DomainField domainField : fields) {
            field = JavaBeanUtil.getJavaBeansField(domainField);
            if (field.getType().equals(FullyQualifiedJavaType.getDateInstance())) {
                clazz.addImportedType(field.getType());
            }
            getter = JavaBeanUtil.getJavaBeansGetter(domainField);
            setter = JavaBeanUtil.getJavaBeansSetter(domainField);

            clazz.addField(field);
            GlennCommentGenerator.addFieldComment(field, domainField);
            clazz.addMethod(getter);
            clazz.addMethod(setter);
        }

        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        list.add(JavaBeanUtil.getGeneratedJavaFile(clazz));
        return list;
    }
}
