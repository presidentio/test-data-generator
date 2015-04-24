package com.presidentio.testdatagenerator.cons;

import java.util.Arrays;
import java.util.List;

/**
 * Created by presidentio on 23.04.15.
 */
public class FileFormatConst {

    public static final String CSV = "csv";
    public static final String TSV = "tsv";
    public static final String JSON = "json";
    public static final String SQL = "sql";
    public static final String ES = "es";

    public static final List<String> ALL = Arrays.asList(CSV, TSV, JSON, SQL, ES);

}
