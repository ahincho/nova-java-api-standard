package pe.edu.galaxy.training.java.libs.api.standard.client;

import java.util.Objects;

/**
 * Información inmutable del cliente que realizó la solicitud.
 * Contiene datos del navegador, sistema operativo, tipo de dispositivo y dirección IP.
 * <p>
 * Usa el patrón Builder para construcción fluida.
 * El factory method {@link #fromUserAgent(String)} delega el parsing a {@link UserAgentParser}.
 */
public final class ClientInfo {

    /** Cadena User-Agent original. */
    private final String userAgent;

    /** Navegador detectado. */
    private final Browser browser;

    /** Versión del navegador detectada. */
    private final String browserVersion;

    /** Sistema operativo detectado. */
    private final OperatingSystem operatingSystem;

    /** Versión del sistema operativo detectada. */
    private final String osVersion;

    /** Tipo de dispositivo detectado. */
    private final DeviceType deviceType;

    /** Dirección IP del cliente (puede ser null). */
    private final String ipAddress;

    /**
     * Constructor privado con todos los campos.
     *
     * @param userAgent       cadena User-Agent original
     * @param browser         navegador detectado
     * @param browserVersion  versión del navegador
     * @param operatingSystem sistema operativo detectado
     * @param osVersion       versión del sistema operativo
     * @param deviceType      tipo de dispositivo
     * @param ipAddress       dirección IP del cliente
     */
    private ClientInfo(String userAgent, Browser browser, String browserVersion,
                       OperatingSystem operatingSystem, String osVersion,
                       DeviceType deviceType, String ipAddress) {
        this.userAgent = userAgent;
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.operatingSystem = operatingSystem;
        this.osVersion = osVersion;
        this.deviceType = deviceType;
        this.ipAddress = ipAddress;
    }

    /**
     * Retorna la cadena User-Agent original.
     *
     * @return cadena User-Agent
     */
    public String userAgent() {
        return userAgent;
    }

    /**
     * Retorna el navegador detectado.
     *
     * @return navegador detectado
     */
    public Browser browser() {
        return browser;
    }

    /**
     * Retorna la versión del navegador detectada.
     *
     * @return versión del navegador
     */
    public String browserVersion() {
        return browserVersion;
    }

    /**
     * Retorna el sistema operativo detectado.
     *
     * @return sistema operativo detectado
     */
    public OperatingSystem operatingSystem() {
        return operatingSystem;
    }

    /**
     * Retorna la versión del sistema operativo detectada.
     *
     * @return versión del sistema operativo
     */
    public String osVersion() {
        return osVersion;
    }

    /**
     * Retorna el tipo de dispositivo detectado.
     *
     * @return tipo de dispositivo
     */
    public DeviceType deviceType() {
        return deviceType;
    }

    /**
     * Retorna la dirección IP del cliente.
     *
     * @return dirección IP (puede ser null)
     */
    public String ipAddress() {
        return ipAddress;
    }

    /**
     * Crea un ClientInfo a partir de una cadena User-Agent.
     * Delega el parsing a {@link UserAgentParser#parse(String)}.
     *
     * @param userAgent cadena User-Agent (puede ser null o vacía)
     * @return ClientInfo con datos parseados o valores UNKNOWN
     */
    public static ClientInfo fromUserAgent(String userAgent) {
        return UserAgentParser.parse(userAgent);
    }

    /**
     * Retorna un nuevo Builder para construir instancias de ClientInfo.
     *
     * @return nuevo Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientInfo that)) return false;
        return Objects.equals(userAgent, that.userAgent)
                && browser == that.browser
                && Objects.equals(browserVersion, that.browserVersion)
                && operatingSystem == that.operatingSystem
                && Objects.equals(osVersion, that.osVersion)
                && deviceType == that.deviceType
                && Objects.equals(ipAddress, that.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAgent, browser, browserVersion, operatingSystem,
                osVersion, deviceType, ipAddress);
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "userAgent='" + userAgent + '\'' +
                ", browser=" + browser +
                ", browserVersion='" + browserVersion + '\'' +
                ", operatingSystem=" + operatingSystem +
                ", osVersion='" + osVersion + '\'' +
                ", deviceType=" + deviceType +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

    /**
     * Builder fluido para construir instancias de ClientInfo.
     */
    public static final class Builder {

        /** Cadena User-Agent. */
        private String userAgent;

        /** Navegador detectado. */
        private Browser browser;

        /** Versión del navegador. */
        private String browserVersion;

        /** Sistema operativo detectado. */
        private OperatingSystem operatingSystem;

        /** Versión del sistema operativo. */
        private String osVersion;

        /** Tipo de dispositivo. */
        private DeviceType deviceType;

        /** Dirección IP del cliente. */
        private String ipAddress;

        private Builder() {
        }

        /**
         * Establece la cadena User-Agent.
         *
         * @param userAgent cadena User-Agent
         * @return este builder
         */
        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * Establece el navegador.
         *
         * @param browser navegador detectado
         * @return este builder
         */
        public Builder browser(Browser browser) {
            this.browser = browser;
            return this;
        }

        /**
         * Establece la versión del navegador.
         *
         * @param browserVersion versión del navegador
         * @return este builder
         */
        public Builder browserVersion(String browserVersion) {
            this.browserVersion = browserVersion;
            return this;
        }

        /**
         * Establece el sistema operativo.
         *
         * @param operatingSystem sistema operativo detectado
         * @return este builder
         */
        public Builder operatingSystem(OperatingSystem operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        /**
         * Establece la versión del sistema operativo.
         *
         * @param osVersion versión del sistema operativo
         * @return este builder
         */
        public Builder osVersion(String osVersion) {
            this.osVersion = osVersion;
            return this;
        }

        /**
         * Establece el tipo de dispositivo.
         *
         * @param deviceType tipo de dispositivo
         * @return este builder
         */
        public Builder deviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        /**
         * Establece la dirección IP del cliente.
         *
         * @param ipAddress dirección IP
         * @return este builder
         */
        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        /**
         * Construye la instancia inmutable de ClientInfo.
         *
         * @return nueva instancia de ClientInfo
         */
        public ClientInfo build() {
            return new ClientInfo(userAgent, browser, browserVersion,
                    operatingSystem, osVersion, deviceType, ipAddress);
        }
    }
}
