/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.*;
import pan.glenn.codegen.context.DomainConfig;
import pan.glenn.codegen.context.GlobalConfig;
import pan.glenn.codegen.generator.util.GlennCommentGenerator;
import pan.glenn.codegen.generator.util.JavaBeanUtil;
import pan.glenn.codegen.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class ServiceGenerator extends GlennGenerator {


    public ServiceGenerator(DomainConfig domainConfig) {
        super(domainConfig);
    }

    public List<GeneratedJavaFile> generate() {
        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        list.add(generateInterface());
        list.add(generateImpl());
        return list;
    }

    private GeneratedJavaFile generateInterface() {
        String interfaceFullName = GlobalConfig.getString("service.pkg") + "."
                + domainConfig.getEntityClassENName() + "Service";
        Interface clazz = new Interface(interfaceFullName);
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.addImportedType(new FullyQualifiedJavaType(GlobalConfig.getString("base.service")));
        clazz.addSuperInterface(new FullyQualifiedJavaType(GlobalConfig.getString("base.service")));

        return JavaBeanUtil.getGeneratedJavaFile(clazz);
    }

    private GeneratedJavaFile generateImpl() {
        String classFullName = GlobalConfig.getString("service.pkg") + ".impl."
                + domainConfig.getEntityClassENName() + "ServiceImpl";
        TopLevelClass clazz = new TopLevelClass(classFullName);
        clazz.setVisibility(JavaVisibility.PUBLIC);

        clazz.addImportedType(GlobalConfig.getString("base.service.impl"));
        clazz.setSuperClass(GlobalConfig.getString("base.service.impl"));

        FullyQualifiedJavaType interfaze = new FullyQualifiedJavaType(GlobalConfig.getString("service.pkg")
            + "."+ domainConfig.getEntityClassENName() + "Service"
        );
        clazz.addImportedType(interfaze);
        clazz.addSuperInterface(interfaze);

        FullyQualifiedJavaType mapper = new FullyQualifiedJavaType(
                JavaBeanUtil.getFullClassName(
                        GlobalConfig.getString("mapper.pkg"),
                        domainConfig.getEntityClassENName() + "Mapper"
                ));
        clazz.addImportedType(mapper);

        String annotationCfg = GlobalConfig.getString("service.class.annotations");
        if (annotationCfg != null && annotationCfg.trim().length() >= 0) {
            String[] annotations = annotationCfg.split(",");
            if (annotations.length > 0) {
                FullyQualifiedJavaType annotationClassType = null;
                for (String annotationClass : annotations) {
                    if (annotationClass == null || annotationClass.trim().length() == 0) {
                        continue;
                    }
                    annotationClassType = new FullyQualifiedJavaType(annotationClass);
                    clazz.addImportedType(annotationClassType);
                    clazz.addAnnotation("@" + annotationClassType.getShortName());
                }
            }
        }

        GlennCommentGenerator.addClassComment(clazz, domainConfig);

        String mapperFieldName = StringUtil.initialsToLowerCase(mapper.getShortName());
        Field mapperField = JavaBeanUtil.getField(mapperFieldName,
                mapper, JavaVisibility.PRIVATE
        );
        clazz.addImportedType("javax.annotation.Resource");
        mapperField.addAnnotation("@Resource");
        clazz.addField(mapperField);

        Method method = JavaBeanUtil.getMethod("getMapper", mapper, JavaVisibility.PUBLIC);
        method.addBodyLine("return " + mapperFieldName + ";");
        clazz.addMethod(method);

        return JavaBeanUtil.getGeneratedJavaFile(clazz);
    }
}
