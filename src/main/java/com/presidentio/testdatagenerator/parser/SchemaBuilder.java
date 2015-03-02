package com.presidentio.testdatagenerator.parser;

import com.presidentio.testdatagenerator.model.Schema;
import com.presidentio.testdatagenerator.model.Template;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitalii_Gergel on 3/2/2015.
 */
public class SchemaBuilder {

    private List<Schema> schemas = new ArrayList<>();

    public SchemaBuilder fromResource(String resource) {
        SchemaSerializer schemaSerializer = new JsonSchemaSerializer();
        try {
            Schema schema = schemaSerializer.deserialize(
                    SchemaBuilder.class.getClassLoader().getResourceAsStream(resource));
            schemas.add(schema);
            return this;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public SchemaBuilder fromFile(String file) {
        SchemaSerializer schemaSerializer = new JsonSchemaSerializer();
        try {
            Schema schema = schemaSerializer.deserialize(new FileInputStream(file));
            schemas.add(schema);
            return this;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public Schema build() {
        Schema schema = null;
        for (Schema schema1 : schemas) {
            if (schema == null) {
                schema = schema1;
            } else {
                schema = merge(schema, schema1);
            }
        }
        if (schema != null) {
            for (Template template : schema.getTemplates()) {
                if (template.getExtend() != null) {
                    for (Template parentTemplate : schema.getTemplates()) {
                        if (parentTemplate.getId().equals(template.getExtend())) {
                            template.setExtendTemplate(parentTemplate);
                            break;
                        }
                    }
                }
            }
        }
        return schema;
    }

    private Schema merge(Schema schema1, Schema schema2) {
        schema1.getTemplates().addAll(schema2.getTemplates());
        schema1.getRoot().addAll(schema2.getRoot());
        schema1.getVariables().addAll(schema2.getVariables());
        if (schema1.getOutput() != null && schema2.getOutput() != null) {
            throw new IllegalArgumentException("Can't merge two outputs");
        }
        return schema1;
    }

}
