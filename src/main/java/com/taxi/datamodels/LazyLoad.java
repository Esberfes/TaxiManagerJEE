package com.taxi.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface LazyLoad<T> {
    List<T> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta);
    int getTotal(Map<String, FilterMeta> filterMeta);

    List<T> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId);

    int getTotal(Map<String, FilterMeta> filterMeta, Long parentId);

    T findById(Long id);
}
