package datamodels;

import org.primefaces.model.SortOrder;
import pojos.Employee;

import java.lang.reflect.Field;
import java.util.Comparator;

public class LazySorter<T> implements Comparator<T> {

    private String sortField;

    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(T employee, T employee1) {
        try {
            Field field1 = Employee.class.getDeclaredField(this.sortField);
            field1.setAccessible(true);

            Object value1 = field1.get(employee);
            Object value2 = field1.get(employee1);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}