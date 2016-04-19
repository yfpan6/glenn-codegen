/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.generator.util;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import pan.glenn.codegen.context.DomainField;

import java.math.BigDecimal;
import java.sql.Types;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class GlennJavaTypeResolver extends JavaTypeResolverDefaultImpl {

    public FullyQualifiedJavaType calculateJavaType(DomainField domainField) {

        FullyQualifiedJavaType answer = null;
//        JdbcTypeInformation jdbcTypeInformation = typeMap
//                .get(domainField.getColumnType());
//
//        if (jdbcTypeInformation == null) {
//            switch (domainField.getColumnType()) {
//                case Types.DECIMAL:
//                case Types.NUMERIC:
//                    if (domainField.getColumnLength() > 18
//                            || forceBigDecimals) {
//                        answer = new FullyQualifiedJavaType(BigDecimal.class
//                                .getName());
//                    } else if (domainField.getColumnLength() > 9) {
//                        answer = new FullyQualifiedJavaType(Long.class.getName());
//                    } else if (domainField.getColumnLength() > 4) {
//                        answer = new FullyQualifiedJavaType(Integer.class.getName());
//                    } else {
//                        answer = new FullyQualifiedJavaType(Short.class.getName());
//                    }
//                    break;
//
//                default:
//                    answer = null;
//                    break;
//            }
//        } else {
//            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
//        }

        return answer;
    }


}
