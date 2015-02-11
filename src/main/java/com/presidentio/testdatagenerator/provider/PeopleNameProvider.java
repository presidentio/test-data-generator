package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vitalii_Gergel on 2/11/2015.
 */
public class PeopleNameProvider extends SelectProvider {

    private static String NAMES;
    public static final String DELIMITER = "\r?\n";

    private String getNames() {
        if (NAMES == null) {
            try (InputStream nameStream = DefaultValueProviderFactory.class.getClassLoader().getResourceAsStream("name.dat")) {
                NAMES = IOUtils.toString(nameStream);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load names", e);
            }
        }
        return NAMES;
    }

    @Override
    public void init(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        propsCopy.put(PropConst.ITEMS, getNames());
        propsCopy.put(PropConst.DELIMITER, DELIMITER);
        super.init(propsCopy);
    }
}
