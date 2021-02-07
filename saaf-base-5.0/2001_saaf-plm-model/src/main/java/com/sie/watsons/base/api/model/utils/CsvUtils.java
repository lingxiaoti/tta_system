package com.sie.watsons.base.api.model.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jooq.tools.csv.CSVParser;
import org.jooq.tools.csv.CSVReader;

public class CsvUtils {

    /**
     * <b>将一个IO流解析，转化数组形式的集合<b>
     *
     * @param in
     *            文件inputStream流
     */
    public static List<String[]> csvToList(InputStream in) throws IOException {
        List<String[]> csvList = new ArrayList<String[]>();
        if (null != in) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(in, "UTF-8"), CSVParser.DEFAULT_SEPARATOR,
                    CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 0);
            String[] strs;
            while ((strs = csvReader.readNext()) != null) {
                csvList.add(strs);
            }
            csvReader.close();
        }
        return csvList;
    }
}
