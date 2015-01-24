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
* *expr* - expression which will be evaluated

##### random

Return random value. Support all data types.

Properties:
* *size* - expression which will be evaluated. Expression can use variables declared in **variables** section. Default value: **10**

##### select

Return on value of specified. Support all data types.

Properties:
* *items* - delimiter separated values, one of them will be selected randomly
* *delimiter* - default value: **,**

##### people-name

Return real name.