package pe.edu.galaxy.training.java.libs.api.standard.query;

/**
 * Operadores de filtrado soportados para criterios de consulta.
 */
public enum FilterOperator {

    /** Igual a. */
    EQ,

    /** No igual a. */
    NEQ,

    /** Mayor que. */
    GT,

    /** Mayor o igual que. */
    GTE,

    /** Menor que. */
    LT,

    /** Menor o igual que. */
    LTE,

    /** Coincidencia parcial (like). */
    LIKE,

    /** Contenido en una lista de valores. */
    IN,

    /** Entre dos valores (inclusivo). */
    BETWEEN,

    /** Es nulo. */
    IS_NULL,

    /** No es nulo. */
    IS_NOT_NULL
}
