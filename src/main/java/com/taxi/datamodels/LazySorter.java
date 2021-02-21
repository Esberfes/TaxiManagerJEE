package com.taxi.datamodels;

import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.Comparator;

public class LazySorter<T> implements Comparator<T> {

    private String sortField;

    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(T obj1, T obj2) {
        try {
            Field field1 = obj1.getClass().getDeclaredField(this.sortField);
            field1.setAccessible(true);

            Object value1 = field1.get(obj1);
            Object value2 = field1.get(obj2);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}