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
public class CountryProvider extends SelectProvider {

    private static String COUNTRIES;
    public static final String DELIMITER = "\r?\n";

    private String getCountries() {
        if (COUNTRIES == null) {
            try (InputStream nameStream = DefaultValueProviderFactory.class.getClassLoader().getResourceAsStream("country.dat")) {
                COUNTRIES = IOUtils.toString(nameStream);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load names", e);
            }
        }
        return COUNTRIES;
    }

    @Override
    public void init(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        propsCopy.put(PropConst.ITEMS, getCountries());
        propsCopy.put(PropConst.DELIMITER, DELIMITER);
        super.init(propsCopy);
    }
}
