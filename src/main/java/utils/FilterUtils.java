package utils;

import org.primefaces.model.FilterMeta;

public abstract class FilterUtils {
    public static Object getFilterFieldValue(FilterMeta filterField) {
        switch (filterField.getFilterMatchMode()) {
            default:
                return "%" + filterField.getFilterValue() + "%";
        }
    }
}
