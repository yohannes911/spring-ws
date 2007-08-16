/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xml.xpath;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Spring {@link FactoryBean} for {@link XPathExpression} object. Facilitates injection of XPath expressions into
 * endpoint beans.
 * <p/>
 * Uses {@link XPathExpressionFactory} underneath, so support is provided for JAXP 1.3, and Jaxen XPaths.
 *
 * @author Arjen Poutsma
 * @see #setExpression(String)
 * @since 1.0
 */
public class XPathExpressionFactoryBean implements FactoryBean, InitializingBean {

    private Properties namespaces;

    private String expressionString;

    private XPathExpression expression;

    /** Sets the XPath expression. Setting this property is required. */
    public void setExpression(String expression) {
        expressionString = expression;
    }

    /** Sets the namespaces for the expressions. The given properties binds string prefixes to string namespaces. */
    public void setNamespaces(Properties namespaces) {
        this.namespaces = namespaces;
    }

    public void afterPropertiesSet() throws IllegalStateException, XPathParseException {
        Assert.notNull(expressionString, "expression is required");
        if (namespaces == null || namespaces.isEmpty()) {
            expression = XPathExpressionFactory.createXPathExpression(expressionString);
        }
        else {
            expression = XPathExpressionFactory.createXPathExpression(expressionString, namespaces);
        }
    }

    public Object getObject() throws Exception {
        return expression;
    }

    public Class getObjectType() {
        return XPathExpression.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
