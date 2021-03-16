package com.taxi.utils;

import org.primefaces.model.FilterMeta;

public abstract class FilterUtils {
    public static Object getFilterFieldValue(FilterMeta filterField) {
        return filterField.getFilterValue() + "%";
    }
}
