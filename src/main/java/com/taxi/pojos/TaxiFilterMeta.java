package com.taxi.pojos;

import org.primefaces.extensions.util.DummyValueExpression;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;

public class TaxiFilterMeta extends FilterMeta {

    private MatchMode matchMode;

    public TaxiFilterMeta(String filterField, Object filterValue, MatchMode matchMode) {
       super(filterField, filterValue);
       this.matchMode = matchMode;
    }

    public TaxiFilterMeta(String filterField, Object filterValue) {
        super(filterField, filterValue);
        this.matchMode = MatchMode.EXACT;
    }

    public MatchMode getFilterMatchMode() {
        return matchMode;
    }

    public void setMatchMode(MatchMode matchMode) {
        this.matchMode = matchMode;
    }
}
