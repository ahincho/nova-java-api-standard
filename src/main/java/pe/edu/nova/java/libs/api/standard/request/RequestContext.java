package pe.edu.nova.java.libs.api.standard.request;

import pe.edu.nova.java.libs.api.standard.client.ClientInfo;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Contexto inmutable de una solicitud de API.
 * Contiene información de trazabilidad, correlación, versión, localización,
 * información del cliente y cabeceras personalizadas.
 * <p>
 * Usa el patrón Builder para construcción fluida.
 * Si no se establece traceId o timestamp, se generan automáticamente.
 */
public final class RequestContext {

    /** Identificador de traza. */
    private final String traceId;

    /** Identificador de correlación (puede ser null). */
    private final String correlationId;

    /** Aplicación origen (puede ser null). */
    private final String sourceApplication;

    /** Versión de la API (puede ser null). */
    private final String apiVersion;

    /** Locale de la solicitud (puede ser null). */
    private final String locale;

    /** Zona horaria de la solicitud (puede ser null). */
    private final String timezone;

    /** Instante de la solicitud. */
    private final Instant timestamp;

    /** Información del cliente (puede ser null). */
    private final ClientInfo clientInfo;

    /** Cabeceras personalizadas inmutables. */
    private final Map<String, String> customHeaders;

    /**
     * Constructor con todos los campos.
     *
     * @param traceId           identificador de traza
     * @param correlationId     identificador de correlación
     * @param sourceApplication aplicación origen
     * @param apiVersion        versión de la API
     * @param locale            locale de la solicitud
     * @param timezone          zona horaria
     * @param timestamp         instante de la solicitud
     * @param clientInfo        información del cliente
     * @param customHeaders     cabeceras personalizadas
     */
    RequestContext(String traceId, String correlationId, String sourceApplication,
                   String apiVersion, String locale, String timezone,
                   Instant timestamp, ClientInfo clientInfo,
                   Map<String, String> customHeaders) {
        this.traceId = traceId;
        this.correlationId = correlationId;
        this.sourceApplication = sourceApplication;
        this.apiVersion = apiVersion;
        this.locale = locale;
        this.timezone = timezone;
        this.timestamp = timestamp;
        this.clientInfo = clientInfo;
        this.customHeaders = customHeaders;
    }

    /**
     * Retorna el identificador de traza.
     *
     * @return identificador de traza
     */
    public String traceId() {
        return traceId;
    }

    /**
     * Retorna el identificador de correlación.
     *
     * @return identificador de correlación (puede ser null)
     */
    public String correlationId() {
        return correlationId;
    }

    /**
     * Retorna la aplicación origen.
     *
     * @return aplicación origen (puede ser null)
     */
    public String sourceApplication() {
        return sourceApplication;
    }

    /**
     * Retorna la versión de la API.
     *
     * @return versión de la API (puede ser null)
     */
    public String apiVersion() {
        return apiVersion;
    }

    /**
     * Retorna el locale de la solicitud.
     *
     * @return locale (puede ser null)
     */
    public String locale() {
        return locale;
    }

    /**
     * Retorna la zona horaria de la solicitud.
     *
     * @return zona horaria (puede ser null)
     */
    public String timezone() {
        return timezone;
    }

    /**
     * Retorna el instante de la solicitud.
     *
     * @return instante de la solicitud
     */
    public Instant timestamp() {
        return timestamp;
    }

    /**
     * Retorna la información del cliente.
     *
     * @return información del cliente (puede ser null)
     */
    public ClientInfo clientInfo() {
        return clientInfo;
    }

    /**
     * Retorna una vista inmutable de las cabeceras personalizadas.
     *
     * @return mapa inmutable de cabeceras personalizadas
     */
    public Map<String, String> customHeaders() {
        return customHeaders;
    }

    /**
     * Crea un RequestContext mínimo con solo traceId y timestamp generados automáticamente.
     *
     * @return nueva instancia de RequestContext con valores mínimos
     */
    public static RequestContext minimal() {
        return new RequestContext(
                UUID.randomUUID().toString(),
                null,
                null,
                null,
                null,
                null,
                Instant.now(),
                null,
                Map.of()
        );
    }

    /**
     * Retorna un nuevo Builder para construir instancias de RequestContext.
     *
     * @return nuevo RequestContextBuilder
     */
    public static RequestContextBuilder builder() {
        return new RequestContextBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestContext that)) return false;
        return Objects.equals(traceId, that.traceId)
                && Objects.equals(correlationId, that.correlationId)
                && Objects.equals(sourceApplication, that.sourceApplication)
                && Objects.equals(apiVersion, that.apiVersion)
                && Objects.equals(locale, that.locale)
                && Objects.equals(timezone, that.timezone)
                && Objects.equals(timestamp, that.timestamp)
                && Objects.equals(clientInfo, that.clientInfo)
                && Objects.equals(customHeaders, that.customHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traceId, correlationId, sourceApplication, apiVersion,
                locale, timezone, timestamp, clientInfo, customHeaders);
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "traceId='" + traceId + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", sourceApplication='" + sourceApplication + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", locale='" + locale + '\'' +
                ", timezone='" + timezone + '\'' +
                ", timestamp=" + timestamp +
                ", clientInfo=" + clientInfo +
                ", customHeaders=" + customHeaders +
                '}';
    }
}
