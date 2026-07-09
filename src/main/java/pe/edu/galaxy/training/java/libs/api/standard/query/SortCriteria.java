package pe.edu.galaxy.training.java.libs.api.standard.query;

/**
 * Criterio de ordenamiento inmutable con campo y dirección.
 *
 * @param field     campo por el cual ordenar (obligatorio)
 * @param direction dirección de ordenamiento (ASC por defecto si es null)
 */
public record SortCriteria(String field, SortDirection direction) {

    /**
     * Constructor compacto con validación y valor por defecto.
     *
     * @throws IllegalArgumentException si field es nulo o en blanco
     */
    public SortCriteria {
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("field es obligatorio");
        }
        if (direction == null) {
            direction = SortDirection.ASC;
        }
    }

    /**
     * Crea un SortCriteria ascendente para el campo dado.
     *
     * @param field campo por el cual ordenar
     * @return nueva instancia de SortCriteria con dirección ASC
     * @throws IllegalArgumentException si field es nulo o en blanco
     */
    public static SortCriteria of(String field) {
        return new SortCriteria(field, SortDirection.ASC);
    }

    /**
     * Crea un SortCriteria con campo y dirección explícita.
     *
     * @param field     campo por el cual ordenar
     * @param direction dirección de ordenamiento
     * @return nueva instancia de SortCriteria
     * @throws IllegalArgumentException si field es nulo o en blanco
     */
    public static SortCriteria of(String field, SortDirection direction) {
        return new SortCriteria(field, direction);
    }
}
