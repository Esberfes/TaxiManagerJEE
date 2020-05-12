CREATE TABLE `conceptos_gastos`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `nombre`        varchar(255) DEFAULT NULL,
    `id_tipo_gasto` int(11) NOT NULL,
    `creado`        datetime     DEFAULT NULL,
    `actualizado`   datetime     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_NOMBRE` (`nombre`),
    KEY `FK_TIPO_GASTO_idx` (`id_tipo_gasto`),
    CONSTRAINT `FK_TIPO_GASTO` FOREIGN KEY (`id_tipo_gasto`) REFERENCES `tipos_gastos` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `conductores`
(
    `id`              int(11)        NOT NULL AUTO_INCREMENT,
    `nombre`          varchar(255)   NOT NULL,
    `id_empresa`      int(11)        NOT NULL,
    `complemento_iva` decimal(10, 2) NOT NULL DEFAULT '5.00',
    `t065`            decimal(10, 2) NOT NULL DEFAULT '100.00',
    `t060`            decimal(10, 2) NOT NULL DEFAULT '140.00',
    `t055`            decimal(10, 2) NOT NULL DEFAULT '200.00',
    `t050`            decimal(10, 2) NOT NULL DEFAULT '10000.00',
    `creado`          datetime                DEFAULT NULL,
    `actualizado`     datetime                DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_NOMBRE` (`nombre`),
    KEY `FK_CONDUCTORES_EMPRESA_idx` (`id_empresa`),
    CONSTRAINT `FK_CONDUCTORES_EMPRESA` FOREIGN KEY (`id_empresa`) REFERENCES `empresas` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 453301
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



CREATE TABLE `empresas`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `nombre`      varchar(255) NOT NULL,
    `creado`      datetime DEFAULT NULL,
    `actualizado` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_NOMBRE` (`nombre`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1513
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `formas_pagos_gastos`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `nombre`      varchar(255) NOT NULL,
    `creado`      datetime DEFAULT NULL,
    `actualizado` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_NOMBRE` (`nombre`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `gastos`
(
    `id`            int(11)        NOT NULL AUTO_INCREMENT,
    `id_licencia`   int(11)        NOT NULL,
    `id_forma_pago` int(11)        NOT NULL,
    `id_concepto`   int(11)        NOT NULL,
    `importe`       decimal(10, 2) NOT NULL DEFAULT '0.00',
    `creado`        datetime                DEFAULT NULL,
    `actualizado`   datetime                DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_GASTOS_LICENCIA_idx` (`id_licencia`),
    KEY `FK_GASTOS_FORMA_idx` (`id_forma_pago`),
    KEY `FK_GASTOS_CONCEPTO_idx` (`id_concepto`),
    CONSTRAINT `FK_GASTOS_CONCEPTO` FOREIGN KEY (`id_concepto`) REFERENCES `conceptos_gastos` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `FK_GASTOS_FORMA` FOREIGN KEY (`id_forma_pago`) REFERENCES `formas_pagos_gastos` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `FK_GASTOS_LICENCIA` FOREIGN KEY (`id_licencia`) REFERENCES `licencias` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `licencias`
(
    `id`          int(11)    NOT NULL AUTO_INCREMENT,
    `codigo`      int(11)    NOT NULL,
    `id_empresa`  int(11)    NOT NULL,
    `es_eurotaxi` tinyint(4) NOT NULL,
    `creado`      datetime DEFAULT NULL,
    `actualizado` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `codigo_UNIQUE` (`codigo`),
    KEY `FK_LICENCIAS_EMPRESAS_idx` (`id_empresa`),
    CONSTRAINT `FK_LICENCIAS_EMPRESAS` FOREIGN KEY (`id_empresa`) REFERENCES `empresas` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `recaudacion_ingresos`
(
    `id`             int(11) NOT NULL,
    `id_recaudacion` int(11)                 DEFAULT NULL,
    `id_conductor`   int(11)                 DEFAULT NULL,
    `dia`            int(2)                  DEFAULT NULL,
    `turno`          enum ('mañana','tarde') DEFAULT NULL,
    `numeracion`     decimal(10, 2)          DEFAULT NULL,
    `anulados`       decimal(10, 2)          DEFAULT NULL,
    `recaudacion`    decimal(10, 2)          DEFAULT NULL,
    `observaciones`  mediumtext,
    `id_estado`      int(11)                 DEFAULT NULL,
    `creado`         datetime                DEFAULT NULL,
    `actualizado`    datetime                DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_INGRESO_RECAUDACION_idx` (`id_recaudacion`),
    KEY `FK_INGRESO_CONDUCTOR_idx` (`id_conductor`),
    KEY `FK_INGRESO_ESTADO_idx` (`id_estado`),
    CONSTRAINT `FK_INGRESO_CONDUCTOR` FOREIGN KEY (`id_conductor`) REFERENCES `conductores` (`id`) ON UPDATE CASCADE,
    CONSTRAINT `FK_INGRESO_ESTADO` FOREIGN KEY (`id_estado`) REFERENCES `recaudaciones_ingresos_estados` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `FK_INGRESO_RECAUDACION` FOREIGN KEY (`id_recaudacion`) REFERENCES `recaudaciones` (`id`) ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `recaudaciones`
(
    `id`                int(11) NOT NULL AUTO_INCREMENT,
    `id_licencia`       int(11) NOT NULL,
    `servicios_inicio`  int(11)        DEFAULT NULL,
    `servicions_fin`    int(11)        DEFAULT NULL,
    `numeracion_inicio` decimal(10, 2) DEFAULT NULL,
    `numeracion_fin`    decimal(10, 2) DEFAULT NULL,
    `km_totales_inicio` int(11)        DEFAULT NULL,
    `km_totales_fin`    int(11)        DEFAULT NULL,
    `km_cargado_inicio` int(11)        DEFAULT NULL,
    `km_cargado_fin`    int(11)        DEFAULT NULL,
    `mes`               int(2)  NOT NULL,
    `ano`               int(4)  NOT NULL,
    `creado`            datetime       DEFAULT NULL,
    `actualizado`       datetime       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_RECAUDACION_LICENCIA_idx` (`id_licencia`),
    CONSTRAINT `FK_RECAUDACION_LICENCIA` FOREIGN KEY (`id_licencia`) REFERENCES `licencias` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `recaudaciones_ingresos_estados`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `nombre`      varchar(255) NOT NULL,
    `creado`      datetime DEFAULT NULL,
    `actualizado` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_NOMBRE` (`nombre`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `tipos_gastos`
(
    `id`             int(11)      NOT NULL AUTO_INCREMENT,
    `nombre`         varchar(255) NOT NULL,
    `es_operacional` tinyint(4)   NOT NULL,
    `creado`         datetime DEFAULT NULL,
    `actualizado`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_NOMBRE` (`nombre`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
