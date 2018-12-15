# Elasticsearch code-parser Ingest Processor [![Build Status](https://travis-ci.com/Poytr1/elasticsearch-ingest-code-parser.svg?branch=master)](https://travis-ci.com/Poytr1/elasticsearch-ingest-code-parser)


This processor can parse Java source code and stores its main fields.

## Usage


```
PUT _ingest/pipeline/code-parser-pipeline
{
  "description": "a pipeline to parse source code",
  "processors": [
    {
      "code_parser" : {
        "field" : "field1",
        "target_fields" : ["class"]
      }
    }
  ]
}

PUT /my-index/my-type/1?pipeline=code-parser-pipeline
{
  "my_field" : "class A { }"
}

GET /my-index/my-type/1
{
  "my_field" : "class A { }"
  "class": "A"
}
```

## Configuration

| Parameter | Use |
| --- | --- |
| some.setting   | Configure x |
| other.setting  | Configure y |

## Setup

In order to install this plugin, you need to create a zip distribution first by running

```bash
gradle clean check
```

This will produce a zip file in `build/distributions`.

After building the zip file, you can install it like this

```bash
bin/elasticsearch-plugin install file:///path/to/ingest-code-parser/build/distribution/ingest-code-parser-0.0.1-SNAPSHOT.zip
```

## Bugs & TODO

* There are always bugs
* and todos...

