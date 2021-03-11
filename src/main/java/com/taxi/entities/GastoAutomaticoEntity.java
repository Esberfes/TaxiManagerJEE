package com.taxi.entities;

import javax.persistence.*;

@Entity
@Table(name = "gasto_automatico")
public class GastoAutomaticoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

}
