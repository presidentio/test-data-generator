/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.testdatagenerator.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Template {

    private String id;

    private String extend;

    @JsonIgnore
    private Template extendTemplate;

    private Integer count;

    private String name;

    private List<Field> fields;

    private List<String> childs = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Template getExtendTemplate() {
        return extendTemplate;
    }

    public void setExtendTemplate(Template extendTemplate) {
        this.extendTemplate = extendTemplate;
    }

    public Integer getCount() {
        if (count == null && extendTemplate != null) {
            return extendTemplate.getCount();
        }
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        if (name == null && extendTemplate != null) {
            return extendTemplate.getName();
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        List<Field> result = new ArrayList<>();
        if (extendTemplate != null && extendTemplate.getFields() != null) {
            result.addAll(extendTemplate.getFields());
        }
        if (fields != null) {
            result.addAll(fields);
        }
        return result;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<String> getChilds() {
        List<String> result = new ArrayList<>();
        if (extendTemplate != null && extendTemplate.getChilds() != null) {
            result.addAll(extendTemplate.getChilds());
        }
        if (childs != null) {
            result.addAll(childs);
        }
        return result;
    }

    public void setChilds(List<String> childs) {
        this.childs = childs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Template template = (Template) o;

        if (name != null ? !name.equals(template.name) : template.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
