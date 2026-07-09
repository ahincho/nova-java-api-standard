package pe.edu.galaxy.training.java.libs.api.standard.metadata;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Metadatos inmutables de una respuesta de API.
 * Contiene información de trazabilidad, versión y campos personalizados.
 * <p>
 * Usa el patrón Builder para construcción fluida.
 * Si no se establece timestamp o traceId, se generan automáticamente.
 */
public final class ApiMetadata {

    /** Instante de creación del metadato. */
    private final Instant timestamp;

    /** Identificador de traza. */
    private final String traceId;

    /** Versión de la API (puede ser null). */
    private final String apiVersion;

    /** Tiempo de procesamiento en milisegundos (puede ser null). */
    private final Long processingTimeMs;

    /** Campos personalizados inmutables. */
    private final Map<String, Object> customFields;

    /**
     * Constructor privado con todos los campos.
     *
     * @param timestamp        instante de creación
     * @param traceId          identificador de traza
     * @param apiVersion       versión de la API
     * @param processingTimeMs tiempo de procesamiento en milisegundos
     * @param customFields     campos personalizados
     */
    private ApiMetadata(Instant timestamp, String traceId, String apiVersion,
                        Long processingTimeMs, Map<String, Object> customFields) {
        this.timestamp = timestamp;
        this.traceId = traceId;
        this.apiVersion = apiVersion;
        this.processingTimeMs = processingTimeMs;
        this.customFields = customFields;
    }

    /**
     * Retorna el instante de creación del metadato.
     *
     * @return instante de creación
     */
    public Instant timestamp() {
        return timestamp;
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
     * Retorna la versión de la API.
     *
     * @return versión de la API (puede ser null)
     */
    public String apiVersion() {
        return apiVersion;
    }

    /**
     * Retorna el tiempo de procesamiento en milisegundos.
     *
     * @return tiempo de procesamiento en milisegundos (puede ser null)
     */
    public Long processingTimeMs() {
        return processingTimeMs;
    }

    /**
     * Retorna una vista inmutable de los campos personalizados.
     *
     * @return mapa inmutable de campos personalizados
     */
    public Map<String, Object> customFields() {
        return customFields;
    }

    /**
     * Crea un ApiMetadata con timestamp y traceId generados automáticamente.
     *
     * @return nueva instancia de ApiMetadata con valores por defecto
     */
    public static ApiMetadata defaults() {
        return new ApiMetadata(
                Instant.now(),
                UUID.randomUUID().toString(),
                null,
                null,
                Map.of()
        );
    }

    /**
     * Retorna un nuevo Builder para construir instancias de ApiMetadata.
     *
     * @return nuevo Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiMetadata that)) return false;
        return Objects.equals(timestamp, that.timestamp)
                && Objects.equals(traceId, that.traceId)
                && Objects.equals(apiVersion, that.apiVersion)
                && Objects.equals(processingTimeMs, that.processingTimeMs)
                && Objects.equals(customFields, that.customFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, traceId, apiVersion, processingTimeMs, customFields);
    }

    @Override
    public String toString() {
        return "ApiMetadata{" +
                "timestamp=" + timestamp +
                ", traceId='" + traceId + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", processingTimeMs=" + processingTimeMs +
                ", customFields=" + customFields +
                '}';
    }

    /**
     * Builder fluido para construir instancias de ApiMetadata.
     */
    public static final class Builder {

        /** Instante de creación. */
        private Instant timestamp;

        /** Identificador de traza. */
        private String traceId;

        /** Versión de la API. */
        private String apiVersion;

        /** Tiempo de procesamiento en milisegundos. */
        private Long processingTimeMs;

        /** Campos personalizados mutables. */
        private final Map<String, Object> customFields = new LinkedHashMap<>();

        private Builder() {
        }

        /**
         * Establece el instante de creación.
         *
         * @param timestamp instante de creación
         * @return este builder
         */
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Establece el identificador de traza.
         *
         * @param traceId identificador de traza
         * @return este builder
         */
        public Builder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        /**
         * Establece la versión de la API.
         *
         * @param apiVersion versión de la API
         * @return este builder
         */
        public Builder apiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        /**
         * Establece el tiempo de procesamiento en milisegundos.
         *
         * @param processingTimeMs tiempo de procesamiento en milisegundos
         * @return este builder
         */
        public Builder processingTimeMs(Long processingTimeMs) {
            this.processingTimeMs = processingTimeMs;
            return this;
        }

        /**
         * Reemplaza los campos personalizados.
         *
         * @param customFields mapa de campos personalizados
         * @return este builder
         */
        public Builder customFields(Map<String, Object> customFields) {
            this.customFields.clear();
            if (customFields != null) {
                this.customFields.putAll(customFields);
            }
            return this;
        }

        /**
         * Agrega un campo personalizado.
         *
         * @param key   clave del campo
         * @param value valor del campo
         * @return este builder
         */
        public Builder customField(String key, Object value) {
            this.customFields.put(key, value);
            return this;
        }

        /**
         * Construye la instancia inmutable de ApiMetadata.
         * Si no se estableció timestamp, asigna {@code Instant.now()}.
         * Si no se estableció traceId, genera UUID automáticamente.
         *
         * @return nueva instancia de ApiMetadata
         */
        public ApiMetadata build() {
            Instant ts = this.timestamp != null ? this.timestamp : Instant.now();
            String tid = this.traceId != null ? this.traceId : UUID.randomUUID().toString();
            Map<String, Object> fields = customFields.isEmpty()
                    ? Map.of()
                    : Collections.unmodifiableMap(new LinkedHashMap<>(customFields));
            return new ApiMetadata(ts, tid, apiVersion, processingTimeMs, fields);
        }
    }
}
