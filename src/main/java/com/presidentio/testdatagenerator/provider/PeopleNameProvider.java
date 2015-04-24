package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.DelimiterConst;
import com.presidentio.testdatagenerator.cons.GenderConst;
import com.presidentio.testdatagenerator.cons.NameTypeConst;
import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;

/**
 * Created by Vitalii_Gergel on 2/11/2015.
 */
public class PeopleNameProvider implements ValueProvider {

    private static String[] MALE_FIRST_NAMES;
    private static String[] FEMALE_FIRST_NAMES;
    private static String[] LAST_NAMES;
    private Random random = new Random();

    private boolean firstName = true;
    private boolean lastName = true;
    private boolean male = true;
    private boolean female = true;

    private String[] getMaleNames() {
        if (MALE_FIRST_NAMES == null) {
            try (InputStream nameStream = DefaultValueProviderFactory.class.getClassLoader()
                    .getResourceAsStream("male-first-name.dat")) {
                String namesStr = IOUtils.toString(nameStream);
                MALE_FIRST_NAMES = namesStr.split(DelimiterConst.COMMA);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load names", e);
            }
        }
        return MALE_FIRST_NAMES;
    }

    private String[] getFemaleNames() {
        if (FEMALE_FIRST_NAMES == null) {
            try (InputStream nameStream = DefaultValueProviderFactory.class.getClassLoader()
                    .getResourceAsStream("female-first-name.dat")) {
                String namesStr = IOUtils.toString(nameStream);
                FEMALE_FIRST_NAMES = namesStr.split(DelimiterConst.COMMA);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load names", e);
            }
        }
        return FEMALE_FIRST_NAMES;
    }

    private String[] getLastNames() {
        if (LAST_NAMES == null) {
            try (InputStream nameStream = DefaultValueProviderFactory.class.getClassLoader()
                    .getResourceAsStream("last-name.dat")) {
                String namesStr = IOUtils.toString(nameStream);
                LAST_NAMES = namesStr.split(DelimiterConst.COMMA);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load names", e);
            }
        }
        return LAST_NAMES;
    }

    @Override
    public void init(Map<String, String> props) {
        String gender = props.get(PropConst.GENDER);
        if (gender == null) {
            gender = GenderConst.ALL;
        }
        switch (gender) {
            case GenderConst.ALL:
                male = true;
                female = true;
                break;
            case GenderConst.MALE:
                male = true;
                female = false;
                break;
            case GenderConst.FEMALE:
                male = false;
                female = true;
                break;
            default:
                throw new IllegalArgumentException("Unknown gender: " + gender);
        }
        String type = props.get(PropConst.TYPE);
        if (type == null) {
            type = NameTypeConst.ALL;
        }
        switch (type) {
            case NameTypeConst.ALL:
                firstName = true;
                lastName = true;
                break;
            case NameTypeConst.FIRST:
                firstName = true;
                lastName = false;
                break;
            case NameTypeConst.LAST:
                firstName = false;
                lastName = true;
                break;
            default:
                throw new IllegalArgumentException("Unknown name type: " + gender);
        }
    }

    @Override
    public Object nextValue(Context context, Field field) {
        String result = "";
        if (firstName) {
            String firstName;
            if (male && female) {
                int index = random.nextInt(getMaleNames().length + getFemaleNames().length);
                if (index < getMaleNames().length) {
                    firstName = getMaleNames()[index];
                } else {
                    firstName = getFemaleNames()[index - getMaleNames().length];
                }
            } else {
                if (male) {
                    int index = random.nextInt(getMaleNames().length);
                    firstName = getMaleNames()[index];
                } else {
                    int index = random.nextInt(getFemaleNames().length);
                    firstName = getFemaleNames()[index];
                }
            }
            result += firstName;
        }
        if (lastName) {
            int index = random.nextInt(getLastNames().length);
            String lastName = getLastNames()[index];
            if (!result.isEmpty()) {
                result += DelimiterConst.SPACE;
            }
            result += lastName;
        }
        return result;
    }
}
