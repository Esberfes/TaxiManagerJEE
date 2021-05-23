package com.taxi.utils;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;

public abstract class FilterUtils {
    public static Object getFilterFieldValue(FilterMeta filterField) {
        if(filterField.getFilterMatchMode() ==  null)
            return filterField.getFilterValue() + "%";
        switch (filterField.getFilterMatchMode()) {
            case EXACT:
                return filterField.getFilterValue();
            default:
                return filterField.getFilterValue() + "%";
        }
    }

    public static String matchModeTranslation(MatchMode matchMode) {
        if(matchMode == null)
            return " LIKE ";
        switch (matchMode) {
            case EXACT:
                return " = ";
            default:
                return " LIKE ";
        }
    }
}
