package pe.edu.nova.java.libs.api.standard.request;

import pe.edu.nova.java.libs.api.standard.client.ClientInfo;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Builder fluido para construir instancias inmutables de {@link RequestContext}.
 * <p>
 * Si no se establece traceId, genera UUID automáticamente.
 * Si no se establece timestamp, asigna {@code Instant.now()}.
 * Realiza copia defensiva inmutable de customHeaders.
 */
public final class RequestContextBuilder {

    /** Identificador de traza. */
    private String traceId;

    /** Identificador de correlación. */
    private String correlationId;

    /** Aplicación origen. */
    private String sourceApplication;

    /** Versión de la API. */
    private String apiVersion;

    /** Locale de la solicitud. */
    private String locale;

    /** Zona horaria de la solicitud. */
    private String timezone;

    /** Instante de la solicitud. */
    private Instant timestamp;

    /** Información del cliente. */
    private ClientInfo clientInfo;

    /** Cabeceras personalizadas mutables. */
    private final Map<String, String> customHeaders = new LinkedHashMap<>();

    /**
     * Constructor con visibilidad de paquete. Usado por {@link RequestContext#builder()}.
     */
    RequestContextBuilder() {
    }

    /**
     * Establece el identificador de traza.
     *
     * @param traceId identificador de traza
     * @return este builder
     */
    public RequestContextBuilder traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    /**
     * Establece el identificador de correlación.
     *
     * @param correlationId identificador de correlación
     * @return este builder
     */
    public RequestContextBuilder correlationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    /**
     * Establece la aplicación origen.
     *
     * @param sourceApplication nombre de la aplicación origen
     * @return este builder
     */
    public RequestContextBuilder sourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
        return this;
    }

    /**
     * Establece la versión de la API.
     *
     * @param apiVersion versión de la API
     * @return este builder
     */
    public RequestContextBuilder apiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    /**
     * Establece el locale de la solicitud.
     *
     * @param locale locale de la solicitud
     * @return este builder
     */
    public RequestContextBuilder locale(String locale) {
        this.locale = locale;
        return this;
    }

    /**
     * Establece la zona horaria de la solicitud.
     *
     * @param timezone zona horaria
     * @return este builder
     */
    public RequestContextBuilder timezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    /**
     * Establece el instante de la solicitud.
     *
     * @param timestamp instante de la solicitud
     * @return este builder
     */
    public RequestContextBuilder timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Establece la información del cliente.
     *
     * @param clientInfo información del cliente
     * @return este builder
     */
    public RequestContextBuilder clientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
        return this;
    }

    /**
     * Reemplaza las cabeceras personalizadas.
     *
     * @param customHeaders mapa de cabeceras personalizadas
     * @return este builder
     */
    public RequestContextBuilder customHeaders(Map<String, String> customHeaders) {
        this.customHeaders.clear();
        if (customHeaders != null) {
            this.customHeaders.putAll(customHeaders);
        }
        return this;
    }

    /**
     * Agrega una cabecera personalizada.
     *
     * @param key   clave de la cabecera
     * @param value valor de la cabecera
     * @return este builder
     */
    public RequestContextBuilder customHeader(String key, String value) {
        this.customHeaders.put(key, value);
        return this;
    }

    /**
     * Construye la instancia inmutable de RequestContext.
     * Si no se estableció traceId, genera UUID automáticamente.
     * Si no se estableció timestamp, asigna {@code Instant.now()}.
     *
     * @return nueva instancia de RequestContext
     */
    public RequestContext build() {
        String tid = this.traceId != null ? this.traceId : UUID.randomUUID().toString();
        Instant ts = this.timestamp != null ? this.timestamp : Instant.now();
        Map<String, String> headers = customHeaders.isEmpty()
                ? Map.of()
                : Collections.unmodifiableMap(new LinkedHashMap<>(customHeaders));
        return new RequestContext(tid, correlationId, sourceApplication, apiVersion,
                locale, timezone, ts, clientInfo, headers);
    }
}
