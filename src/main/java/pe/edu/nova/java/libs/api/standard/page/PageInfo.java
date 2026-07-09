package pe.edu.nova.java.libs.api.standard.page;

/**
 * Información de paginación inmutable.
 * Calcula automáticamente totalPages, hasNext y hasPrevious.
 *
 * @param page          página actual (base 0)
 * @param size          tamaño de página
 * @param totalElements total de elementos
 * @param totalPages    total de páginas (calculado)
 * @param hasNext       indica si hay página siguiente (calculado)
 * @param hasPrevious   indica si hay página anterior (calculado)
 */
public record PageInfo(
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {

    /**
     * Constructor compacto con validación y cálculos automáticos.
     *
     * @throws IllegalArgumentException si size &lt;= 0
     * @throws IllegalArgumentException si page &lt; 0
     * @throws IllegalArgumentException si totalElements &lt; 0
     */
    public PageInfo {
        if (size <= 0) {
            throw new IllegalArgumentException("size debe ser positivo, recibido: " + size);
        }
        if (page < 0) {
            throw new IllegalArgumentException("page no debe ser negativo, recibido: " + page);
        }
        if (totalElements < 0) {
            throw new IllegalArgumentException("totalElements no debe ser negativo, recibido: " + totalElements);
        }
        totalPages = (int) Math.ceil((double) totalElements / size);
        hasNext = page < totalPages - 1;
        hasPrevious = page > 0;
    }

    /**
     * Crea un PageInfo con los valores proporcionados.
     * Los campos totalPages, hasNext y hasPrevious se calculan automáticamente.
     *
     * @param page          página actual (base 0)
     * @param size          tamaño de página (debe ser positivo)
     * @param totalElements total de elementos (no negativo)
     * @return nueva instancia de PageInfo
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public static PageInfo of(int page, int size, long totalElements) {
        return new PageInfo(page, size, totalElements, 0, false, false);
    }
}
