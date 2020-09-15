# test-data-generator
Tools to generate data for testing

[![Build Status](https://travis-ci.org/presidentio/test-data-generator.svg?branch=master)](https://travis-ci.org/presidentio/test-data-generator)

## Usage

```
<dependency>
    <groupId>com.presidentio</groupId>
    <artifactId>test-data-generator</artifactId>
    <version>1.0.4</version>
</dependency>
```

```
Schema schema = new SchemaBuilder().fromResource("schema.json").build();
OneTimeGenerator generator = new OneTimeGenerator();
generator.generate(schema);
schema.getOutput();
```

## Value Providers

##### const

Return const value for each entity. Support all data types.

Properties:
* *value* - required. Value used as constant. Example: "123"

##### email

Generate emails with const domain. Support only **string** data type.

Properties:
* *domain* - email domain. Example: "gmail.com". Default value: **email.com**

##### expr

Evaluate expression. Expression can use variables declared in **variables** section and parent variable which point
to parent entity. See details here http://mvel.codehaus.org/ . Support all data types.

Properties:
* *expr* - required, expression which will be evaluated

##### random

Return random value. Support all data types.

Properties:
* *size* - expression which will be evaluated. Expression can use variables declared in **variables** section. Default value: **10**

##### select

Return on value of specified. Support all data types.

Properties:
* *items* - required, delimiter separated values, one of them will be selected randomly
* *delimiter* - default value: **,**

##### people-name

Return real people name.

##### country

Return real country name.

##### time

Return real time value in milliseconds

## Output

##### console

Print all generate data to the System.out

##### file

Write sql formatted data to file.

Properties:
* *file* - required, file to save data
* *format* - one of csv, tsv, sv (requires *delimiter* property, optionally also a boolean *header* property), sql, es (requires *index* property), default is csv

Note: Be care with first three formats, they works well only with identically structured templates  

##### sql-direct

Write sql formatted by jdbc connection.

Properties:
* *jdbcDriver* - jdbc driver to init jdbc connection and check does it support multi insert
* *connectionUrl* - jdbc connection url with username and password if it requires

##### es-direct

Write Elastic Search bulk formatted data to file.

Properties:
* *host* - required, elastic search node host
* *port* - required, elastic search node port
* *clusterName* - required, elastic search cluster name
* *index* - required, index name

## Placeholders

You can user placeholder in any string field value. Placeholder format is ${placeholder_name}.

##### Available:
* ${tmp} - replacing with path to tmp directory


## Schema extending

##### Merge two schemas in one:

```
Schema schema = new SchemaBuilder()
    .fromResource("schema1.json")
    .fromResource("schema2.json")
    .build();
...
```

##### Template extending
```
...
  "templates": [
    {
      "id": "user",
      "count": 10,
      "name": "user",
      "fields": [
        {
          "name": "id",
          "type": "long",
          "provider": {
            "name": "expr",
            "props": {
              "expr": "userId++"
            }
          }
        },
        {
          "name": "email",
          "type": "string",
          "provider": {
            "name": "email"
          }
        },
        {
          "name": "name",
          "type": "string",
          "provider": {
            "name": "people-name"
          }
        }
      ],
      "children": [
        "training1"
      ]
    },
    {
      "id": "user1",
      "count": 10,
      "extend": "user"
    },
...
```

## Schema example

```
{
    "output": {
        "type": "file",
        "props": {
            "file": "${tmp}/a.sql",
            "format": "sql"
        }
    },
    "templates": [
        {
            "id": "user1",
            "count": 10,
            "name": "user",
            "fields": [
                {
                    "name": "id",
                    "type": "long",
                    "provider": {
                        "name": "expr",
                        "props": {
                            "expr": "userId++"
                        }
                    }
                },
                {
                    "name": "email",
                    "type": "string",
                    "provider": {
                        "name": "email"
                    }
                } ,
                {
                    "name": "name",
                    "type": "string",
                    "provider": {
                        "name": "people-name"
                    }
                }
            ],
            "children": [
                "training1"
            ]
        },
        {
            "id": "user2",
            "count": 1,
            "name": "user",
            "fields": [
                {
                    "name": "id",
                    "type": "long",
                    "provider": {
                        "name": "expr",
                        "props": {
                            "expr": "userId++"
                        }
                    }
                },
                {
                    "name": "email",
                    "type": "string",
                    "provider": {
                        "name": "const",
                        "props": {
                            "value": "test@email.com"
                        }
                    }
                }
            ],
            "children": [
                "training1"
            ]
        },
        {
            "id": "training1",
            "count": 5,
            "name": "training",
            "fields": [
                {
                    "name": "id",
                    "type": "long",
                    "provider": {
                        "name": "expr",
                        "props": {
                            "expr": "trainingId++"
                        }
                    }
                },
                {
                    "name": "name",
                    "type": "string",
                    "provider": {
                        "name": "random",
                        "props": {
                            "size": 10
                        }
                    }
                },
                {
                    "name": "week",
                    "type": "int",
                    "provider": {
                        "name": "expr",
                        "props": {
                            "expr": "0 | 1 | 8"
                        }
                    }
                },
                {
                    "name": "userId",
                    "type": "long",
                    "provider": {
                        "name": "expr",
                        "props": {
                            "expr": "parent.id"
                        }
                    }
                }
            ],
            "children":[
                "exercise1"
            ]
        },
        {
            "id": "exercise1",
            "count": 5,
            "name": "exercise",
            "fields": [
                {
                    "name": "id",
                    "type": "long",
                    "provider": {
                        "name": "expr",
                        "props": {
                            "expr": "exerciseId++"
                        }
                    }
                },
                {
                    "name": "name",
                    "type": "string",
                    "provider": {
                        "name": "random",
                        "props": {
                            "size": 10
                        }
                    }
                },
                {
                    "name": "userId",
                    "type": "long",
                    "provider": {
                        "name": "expr",
                        "props": {
                            "expr": "parent.parent.id"
                        }
                    }
                },
                {
                    "name": "trainingId",
                    "type": "long",
                    "provider": {
                        "name": "expr",
                        "props": {
                            "expr": "parent.id"
                        }
                    }
                }
            ]
        }
    ],
    "variables": [
        {
            "name": "userId",
            "type": "long",
            "initValue": "1"
        },
        {
            "name": "trainingId",
            "type": "long",
            "initValue": "1"
        },
        {
            "name": "exerciseId",
            "type": "long",
            "initValue": "1"
        }
    ],
    "root": [
        "user1",
        "user2"
    ]
}
```

Licensing
=========

[![][license img]][license]

This software is licensed under the terms in the file named "LICENSE" in this directory.


[license]:LICENSE
[license img]:https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg
