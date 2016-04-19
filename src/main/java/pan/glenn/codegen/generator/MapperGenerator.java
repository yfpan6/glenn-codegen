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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class MapperGenerator extends GlennGenerator {

    public MapperGenerator(DomainConfig domainConfig) {
        super(domainConfig);
    }

    public List<GeneratedJavaFile> generate() {
        String classFullName = GlobalConfig.getString("mapper.pkg") + "."
                + domainConfig.getEntityClassENName() + "Mapper";
        Interface clazz = new Interface(classFullName);
        clazz.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType baseMapper = new FullyQualifiedJavaType(GlobalConfig.getString("base.mapper"));
        clazz.addImportedType(baseMapper);
        clazz.addSuperInterface(baseMapper);

        String annotationCfg = GlobalConfig.getString("mapper.class.annotations");
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

        GlennCommentGenerator.addJavaComment(clazz, domainConfig);

        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        list.add(JavaBeanUtil.getGeneratedJavaFile(clazz));
        return list;
    }
}
