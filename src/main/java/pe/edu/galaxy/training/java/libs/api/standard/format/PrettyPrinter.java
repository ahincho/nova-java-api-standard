package pe.edu.galaxy.training.java.libs.api.standard.format;

import pe.edu.galaxy.training.java.libs.api.standard.client.Browser;
import pe.edu.galaxy.training.java.libs.api.standard.client.ClientInfo;
import pe.edu.galaxy.training.java.libs.api.standard.client.DeviceType;
import pe.edu.galaxy.training.java.libs.api.standard.client.OperatingSystem;
import pe.edu.galaxy.training.java.libs.api.standard.error.ApiError;
import pe.edu.galaxy.training.java.libs.api.standard.link.ApiLink;
import pe.edu.galaxy.training.java.libs.api.standard.metadata.ApiMetadata;
import pe.edu.galaxy.training.java.libs.api.standard.page.PageInfo;
import pe.edu.galaxy.training.java.libs.api.standard.ratelimit.RateLimitInfo;
import pe.edu.galaxy.training.java.libs.api.standard.request.RequestContext;
import pe.edu.galaxy.training.java.libs.api.standard.response.ApiResponse;
import pe.edu.galaxy.training.java.libs.api.standard.response.ResponseBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase utilitaria para serializar objetos de la librería a formato texto legible clave-valor.
 * Soporta round-trip (format → parse) para validación en tests.
 */
public final class PrettyPrinter {

    /**
     * Constructor privado para evitar instanciación (clase utilitaria).
     */
    private PrettyPrinter() {
    }

    /**
     * Formatea un ApiResponse a texto legible clave-valor.
     * Omite la sección de errores si la lista está vacía.
     * Representa data nulo como "null".
     *
     * @param response respuesta a formatear
     * @param <T>      tipo del dato
     * @return representación en texto
     */
    public static <T> String format(ApiResponse<T> response) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ApiResponse]\n");
        sb.append("success=").append(response.success()).append('\n');
        sb.append("status=").append(response.status()).append('\n');
        sb.append("data=").append(response.data() == null ? "null" : response.data().toString()).append('\n');

        // Sección de metadatos
        if (response.metadata() != null) {
            sb.append("[Metadata]\n");
            sb.append("timestamp=").append(response.metadata().timestamp()).append('\n');
            sb.append("traceId=").append(response.metadata().traceId()).append('\n');
            sb.append("apiVersion=").append(nullSafe(response.metadata().apiVersion())).append('\n');
            sb.append("processingTimeMs=").append(nullSafe(response.metadata().processingTimeMs())).append('\n');
        }

        // Sección de errores — se omite si está vacía
        if (!response.errors().isEmpty()) {
            sb.append("[Errors]\n");
            for (int i = 0; i < response.errors().size(); i++) {
                ApiError error = response.errors().get(i);
                sb.append("error[").append(i).append("].code=").append(error.code()).append('\n');
                sb.append("error[").append(i).append("].message=").append(error.message()).append('\n');
                sb.append("error[").append(i).append("].field=").append(nullSafe(error.field())).append('\n');
            }
        }

        // Sección de enlaces
        if (!response.links().isEmpty()) {
            sb.append("[Links]\n");
            for (int i = 0; i < response.links().size(); i++) {
                ApiLink link = response.links().get(i);
                sb.append("link[").append(i).append("].rel=").append(link.rel()).append('\n');
                sb.append("link[").append(i).append("].href=").append(link.href()).append('\n');
                sb.append("link[").append(i).append("].method=").append(nullSafe(link.method())).append('\n');
            }
        }

        // Sección de RateLimitInfo
        if (response.rateLimitInfo() != null) {
            sb.append("[RateLimit]\n");
            sb.append("limit=").append(response.rateLimitInfo().limit()).append('\n');
            sb.append("remaining=").append(response.rateLimitInfo().remaining()).append('\n');
            sb.append("resetAt=").append(response.rateLimitInfo().resetAt()).append('\n');
        }

        // Sección de PageInfo
        if (response.pageInfo() != null) {
            sb.append("[PageInfo]\n");
            sb.append("page=").append(response.pageInfo().page()).append('\n');
            sb.append("size=").append(response.pageInfo().size()).append('\n');
            sb.append("totalElements=").append(response.pageInfo().totalElements()).append('\n');
        }

        return sb.toString();
    }

    /**
     * Formatea un RequestContext a texto legible clave-valor.
     *
     * @param context contexto a formatear
     * @return representación en texto
     */
    public static String format(RequestContext context) {
        StringBuilder sb = new StringBuilder();
        sb.append("[RequestContext]\n");
        sb.append("traceId=").append(nullSafe(context.traceId())).append('\n');
        sb.append("correlationId=").append(nullSafe(context.correlationId())).append('\n');
        sb.append("sourceApplication=").append(nullSafe(context.sourceApplication())).append('\n');
        sb.append("apiVersion=").append(nullSafe(context.apiVersion())).append('\n');
        sb.append("locale=").append(nullSafe(context.locale())).append('\n');
        sb.append("timezone=").append(nullSafe(context.timezone())).append('\n');
        sb.append("timestamp=").append(context.timestamp()).append('\n');

        if (context.clientInfo() != null) {
            sb.append("[ClientInfo]\n");
            sb.append(formatClientInfoBody(context.clientInfo()));
        }

        if (!context.customHeaders().isEmpty()) {
            sb.append("[CustomHeaders]\n");
            context.customHeaders().forEach((k, v) ->
                    sb.append(k).append('=').append(v).append('\n'));
        }

        return sb.toString();
    }

    /**
     * Formatea un ClientInfo a texto legible.
     *
     * @param clientInfo información del cliente a formatear
     * @return representación en texto
     */
    public static String format(ClientInfo clientInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ClientInfo]\n");
        sb.append(formatClientInfoBody(clientInfo));
        return sb.toString();
    }

    /**
     * Formatea el cuerpo de un ClientInfo a texto legible clave-valor (sin encabezado de sección).
     *
     * @param clientInfo información del cliente a formatear
     * @return representación en texto del cuerpo
     */
    private static String formatClientInfoBody(ClientInfo clientInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("userAgent=").append(nullSafe(clientInfo.userAgent())).append('\n');
        sb.append("browser=").append(clientInfo.browser()).append('\n');
        sb.append("browserVersion=").append(nullSafe(clientInfo.browserVersion())).append('\n');
        sb.append("operatingSystem=").append(clientInfo.operatingSystem()).append('\n');
        sb.append("osVersion=").append(nullSafe(clientInfo.osVersion())).append('\n');
        sb.append("deviceType=").append(clientInfo.deviceType()).append('\n');
        sb.append("ipAddress=").append(nullSafe(clientInfo.ipAddress())).append('\n');
        return sb.toString();
    }

    /**
     * Parsea una representación de texto de ApiResponse.
     * Usado para validar la propiedad round-trip en tests.
     *
     * @param text     texto a parsear
     * @param dataType tipo del dato (String.class para round-trip simple)
     * @param <T>      tipo del dato
     * @return ApiResponse parseado
     */
    @SuppressWarnings("unchecked")
    public static <T> ApiResponse<T> parseApiResponse(String text, Class<T> dataType) {
        Map<String, String> sections = parseSections(text);
        Map<String, String> responseFields = parseFields(sections.getOrDefault("ApiResponse", ""));

        int status = Integer.parseInt(responseFields.getOrDefault("status", "200"));
        String dataStr = responseFields.getOrDefault("data", "null");
        T data = "null".equals(dataStr) ? null : (T) dataStr;

        ResponseBuilder<T> builder = ApiResponse.<T>builder()
                .data(data)
                .status(status);

        // Parsear metadatos
        if (sections.containsKey("Metadata")) {
            Map<String, String> metaFields = parseFields(sections.get("Metadata"));
            ApiMetadata.Builder metaBuilder = ApiMetadata.builder();
            if (metaFields.containsKey("timestamp") && !"null".equals(metaFields.get("timestamp"))) {
                metaBuilder.timestamp(Instant.parse(metaFields.get("timestamp")));
            }
            if (metaFields.containsKey("traceId") && !"null".equals(metaFields.get("traceId"))) {
                metaBuilder.traceId(metaFields.get("traceId"));
            }
            if (metaFields.containsKey("apiVersion") && !"null".equals(metaFields.get("apiVersion"))) {
                metaBuilder.apiVersion(metaFields.get("apiVersion"));
            }
            if (metaFields.containsKey("processingTimeMs") && !"null".equals(metaFields.get("processingTimeMs"))) {
                metaBuilder.processingTimeMs(Long.parseLong(metaFields.get("processingTimeMs")));
            }
            builder.metadata(metaBuilder.build());
        }

        // Parsear errores
        if (sections.containsKey("Errors")) {
            Map<String, String> errorFields = parseFields(sections.get("Errors"));
            Map<Integer, Map<String, String>> errorsByIndex = groupByIndex(errorFields, "error");
            List<ApiError> errors = new ArrayList<>();
            for (var entry : errorsByIndex.entrySet()) {
                Map<String, String> ef = entry.getValue();
                String code = ef.getOrDefault("code", "ERROR");
                String message = ef.getOrDefault("message", "");
                String field = "null".equals(ef.get("field")) ? null : ef.get("field");
                if (field != null) {
                    errors.add(ApiError.of(code, message, field));
                } else {
                    errors.add(ApiError.of(code, message));
                }
            }
            builder.errors(errors);
        }

        // Parsear enlaces
        if (sections.containsKey("Links")) {
            Map<String, String> linkFields = parseFields(sections.get("Links"));
            Map<Integer, Map<String, String>> linksByIndex = groupByIndex(linkFields, "link");
            List<ApiLink> links = new ArrayList<>();
            for (var entry : linksByIndex.entrySet()) {
                Map<String, String> lf = entry.getValue();
                String rel = lf.getOrDefault("rel", "self");
                String href = lf.getOrDefault("href", "/");
                String method = "null".equals(lf.get("method")) ? null : lf.get("method");
                links.add(ApiLink.of(rel, href, method));
            }
            builder.links(links);
        }

        // Parsear rate limit
        if (sections.containsKey("RateLimit")) {
            Map<String, String> rlFields = parseFields(sections.get("RateLimit"));
            int limit = Integer.parseInt(rlFields.getOrDefault("limit", "100"));
            int remaining = Integer.parseInt(rlFields.getOrDefault("remaining", "99"));
            Instant resetAt = Instant.parse(rlFields.getOrDefault("resetAt", Instant.now().toString()));
            builder.rateLimitInfo(RateLimitInfo.of(limit, remaining, resetAt));
        }

        // Parsear información de paginación
        if (sections.containsKey("PageInfo")) {
            Map<String, String> piFields = parseFields(sections.get("PageInfo"));
            int page = Integer.parseInt(piFields.getOrDefault("page", "0"));
            int size = Integer.parseInt(piFields.getOrDefault("size", "10"));
            long totalElements = Long.parseLong(piFields.getOrDefault("totalElements", "0"));
            builder.page(PageInfo.of(page, size, totalElements));
        }

        return builder.build();
    }

    /**
     * Parsea una representación de texto de RequestContext.
     * Usado para validar la propiedad round-trip en tests.
     *
     * @param text texto a parsear
     * @return RequestContext parseado
     */
    public static RequestContext parseRequestContext(String text) {
        Map<String, String> sections = parseSections(text);
        Map<String, String> rcFields = parseFields(sections.getOrDefault("RequestContext", ""));

        var builder = RequestContext.builder();

        if (rcFields.containsKey("traceId") && !"null".equals(rcFields.get("traceId"))) {
            builder.traceId(rcFields.get("traceId"));
        }
        if (rcFields.containsKey("correlationId") && !"null".equals(rcFields.get("correlationId"))) {
            builder.correlationId(rcFields.get("correlationId"));
        }
        if (rcFields.containsKey("sourceApplication") && !"null".equals(rcFields.get("sourceApplication"))) {
            builder.sourceApplication(rcFields.get("sourceApplication"));
        }
        if (rcFields.containsKey("apiVersion") && !"null".equals(rcFields.get("apiVersion"))) {
            builder.apiVersion(rcFields.get("apiVersion"));
        }
        if (rcFields.containsKey("locale") && !"null".equals(rcFields.get("locale"))) {
            builder.locale(rcFields.get("locale"));
        }
        if (rcFields.containsKey("timezone") && !"null".equals(rcFields.get("timezone"))) {
            builder.timezone(rcFields.get("timezone"));
        }
        if (rcFields.containsKey("timestamp") && !"null".equals(rcFields.get("timestamp"))) {
            builder.timestamp(Instant.parse(rcFields.get("timestamp")));
        }

        // Parsear información del cliente
        if (sections.containsKey("ClientInfo")) {
            Map<String, String> ciFields = parseFields(sections.get("ClientInfo"));
            var ciBuilder = ClientInfo.builder();
            ciBuilder.userAgent(nullableField(ciFields.get("userAgent")));
            ciBuilder.browser(ciFields.containsKey("browser") ? Browser.valueOf(ciFields.get("browser")) : Browser.UNKNOWN);
            ciBuilder.browserVersion(nullableField(ciFields.get("browserVersion")));
            ciBuilder.operatingSystem(ciFields.containsKey("operatingSystem") ? OperatingSystem.valueOf(ciFields.get("operatingSystem")) : OperatingSystem.UNKNOWN);
            ciBuilder.osVersion(nullableField(ciFields.get("osVersion")));
            ciBuilder.deviceType(ciFields.containsKey("deviceType") ? DeviceType.valueOf(ciFields.get("deviceType")) : DeviceType.UNKNOWN);
            ciBuilder.ipAddress(nullableField(ciFields.get("ipAddress")));
            builder.clientInfo(ciBuilder.build());
        }

        // Parsear cabeceras personalizadas
        if (sections.containsKey("CustomHeaders")) {
            Map<String, String> headerFields = parseFields(sections.get("CustomHeaders"));
            builder.customHeaders(headerFields);
        }

        return builder.build();
    }

    // --- Métodos auxiliares internos de parseo ---

    /**
     * Parsea el texto en secciones delimitadas por encabezados entre corchetes.
     *
     * @param text texto a parsear
     * @return mapa de nombre de sección a contenido
     */
    private static Map<String, String> parseSections(String text) {
        Map<String, String> sections = new LinkedHashMap<>();
        String currentSection = null;
        StringBuilder currentContent = new StringBuilder();

        for (String line : text.split("\n")) {
            if (line.startsWith("[") && line.endsWith("]")) {
                if (currentSection != null) {
                    sections.put(currentSection, currentContent.toString());
                }
                currentSection = line.substring(1, line.length() - 1);
                currentContent = new StringBuilder();
            } else if (currentSection != null) {
                currentContent.append(line).append('\n');
            }
        }
        if (currentSection != null) {
            sections.put(currentSection, currentContent.toString());
        }
        return sections;
    }

    /**
     * Parsea el contenido de una sección en pares clave-valor separados por '='.
     *
     * @param sectionContent contenido de la sección a parsear
     * @return mapa de clave a valor
     */
    private static Map<String, String> parseFields(String sectionContent) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (String line : sectionContent.split("\n")) {
            int eqIdx = line.indexOf('=');
            if (eqIdx > 0) {
                String key = line.substring(0, eqIdx);
                String value = line.substring(eqIdx + 1);
                fields.put(key, value);
            }
        }
        return fields;
    }

    /**
     * Agrupa campos indexados por su índice numérico.
     * Patrón esperado: prefijo[índice].nombreCampo
     *
     * @param fields campos a agrupar
     * @param prefix prefijo del campo indexado
     * @return mapa de índice a mapa de campos
     */
    private static Map<Integer, Map<String, String>> groupByIndex(Map<String, String> fields, String prefix) {
        Map<Integer, Map<String, String>> grouped = new LinkedHashMap<>();
        for (var entry : fields.entrySet()) {
            String key = entry.getKey();
            // Patrón: prefijo[índice].nombreCampo
            if (key.startsWith(prefix + "[")) {
                int closeBracket = key.indexOf(']');
                if (closeBracket > 0) {
                    int index = Integer.parseInt(key.substring(prefix.length() + 1, closeBracket));
                    String fieldName = key.substring(closeBracket + 2); // saltar "]."
                    grouped.computeIfAbsent(index, k -> new LinkedHashMap<>()).put(fieldName, entry.getValue());
                }
            }
        }
        return grouped;
    }

    /**
     * Retorna la representación en texto de un valor, o "null" si es nulo.
     *
     * @param value valor a convertir
     * @return representación en texto o "null"
     */
    private static String nullSafe(Object value) {
        return value == null ? "null" : value.toString();
    }

    /**
     * Convierte un campo de texto a null si su valor es la cadena "null".
     *
     * @param value valor a evaluar
     * @return el valor original o null si es la cadena "null"
     */
    private static String nullableField(String value) {
        return "null".equals(value) ? null : value;
    }
}
