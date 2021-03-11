package com.taxi.pojos;

import java.util.Date;

public class EmptyLicencia extends Licencia{
    public EmptyLicencia() {
        this.id = -1L;
        this.empresa = null;
        this.codigo = -1;
        this.es_eurotaxi = true;
        this.creado = new Date();
        this.actualizado = new Date();
    }
}
