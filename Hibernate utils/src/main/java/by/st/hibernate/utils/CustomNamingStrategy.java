package by.st.hibernate.utils;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * Created by alian on 31.01.2016.
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {

    @Override
    public String classToTableName(String className) {
        return "T_" + super.classToTableName(className).toUpperCase();
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return "F_" + super.propertyToColumnName(propertyName).toUpperCase();
    }
}
