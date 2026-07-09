package pe.edu.nova.java.libs.api.standard.ratelimit;

import java.time.Instant;

/**
 * Información de rate limiting inmutable.
 *
 * @param limit     límite máximo de solicitudes (debe ser positivo)
 * @param remaining solicitudes restantes (no negativo, no mayor que limit)
 * @param resetAt   instante en que se reinicia el límite
 */
public record RateLimitInfo(int limit, int remaining, Instant resetAt) {

    /**
     * Constructor compacto con validación.
     *
     * @throws IllegalArgumentException si limit &lt;= 0
     * @throws IllegalArgumentException si remaining &lt; 0
     * @throws IllegalArgumentException si remaining &gt; limit
     */
    public RateLimitInfo {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit debe ser positivo, recibido: " + limit);
        }
        if (remaining < 0) {
            throw new IllegalArgumentException("remaining no debe ser negativo, recibido: " + remaining);
        }
        if (remaining > limit) {
            throw new IllegalArgumentException(
                    "remaining no puede exceder limit: " + remaining + " > " + limit);
        }
    }

    /**
     * Crea un RateLimitInfo con los valores proporcionados.
     *
     * @param limit     límite máximo de solicitudes
     * @param remaining solicitudes restantes
     * @param resetAt   instante de reinicio
     * @return nueva instancia de RateLimitInfo
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public static RateLimitInfo of(int limit, int remaining, Instant resetAt) {
        return new RateLimitInfo(limit, remaining, resetAt);
    }
}
