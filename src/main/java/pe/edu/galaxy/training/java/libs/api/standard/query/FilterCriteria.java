package pe.edu.galaxy.training.java.libs.api.standard.query;

import java.util.List;

/**
 * Criterio de filtrado inmutable con campo, operador y valor.
 *
 * @param field    campo a filtrar (obligatorio)
 * @param operator operador de filtrado (obligatorio)
 * @param value    valor del filtro (puede ser null para IS_NULL/IS_NOT_NULL)
 */
public record FilterCriteria(String field, FilterOperator operator, Object value) {

    /**
     * Constructor compacto con validación.
     *
     * @throws IllegalArgumentException si field es nulo o en blanco
     * @throws IllegalArgumentException si operator es nulo
     */
    public FilterCriteria {
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("field es obligatorio");
        }
        if (operator == null) {
            throw new IllegalArgumentException("operator es obligatorio");
        }
    }

    /**
     * Crea un FilterCriteria de igualdad.
     *
     * @param field campo a filtrar
     * @param value valor esperado
     * @return nueva instancia de FilterCriteria con operador EQ
     * @throws IllegalArgumentException si field es nulo o en blanco
     */
    public static FilterCriteria eq(String field, Object value) {
        return new FilterCriteria(field, FilterOperator.EQ, value);
    }

    /**
     * Crea un FilterCriteria de coincidencia parcial.
     *
     * @param field   campo a filtrar
     * @param pattern patrón de búsqueda
     * @return nueva instancia de FilterCriteria con operador LIKE
     * @throws IllegalArgumentException si field es nulo o en blanco
     */
    public static FilterCriteria like(String field, String pattern) {
        return new FilterCriteria(field, FilterOperator.LIKE, pattern);
    }

    /**
     * Crea un FilterCriteria de rango.
     *
     * @param field campo a filtrar
     * @param from  valor inferior del rango
     * @param to    valor superior del rango
     * @return nueva instancia de FilterCriteria con operador BETWEEN
     * @throws IllegalArgumentException si field es nulo o en blanco
     */
    public static FilterCriteria between(String field, Object from, Object to) {
        return new FilterCriteria(field, FilterOperator.BETWEEN, List.of(from, to));
    }

    /**
     * Crea un FilterCriteria para verificar nulidad.
     *
     * @param field campo a verificar
     * @return nueva instancia de FilterCriteria con operador IS_NULL
     * @throws IllegalArgumentException si field es nulo o en blanco
     */
    public static FilterCriteria isNull(String field) {
        return new FilterCriteria(field, FilterOperator.IS_NULL, null);
    }
}
