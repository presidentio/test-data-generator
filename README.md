# test-data-generator
Tools to generate data for testing

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

Return real name.

## Output

##### console

Print all generate data to the System.out

##### sql-file

Write sql formatted data to file.

Properties:
* *file* - required, file to save sql.

## Schema example

```
{
  "output": {
    "type": "sql-file",
    "props": {
      "file": "./a.sql"
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
        }
      ],
      "child": [
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
      "child": [
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
      "child":[
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
