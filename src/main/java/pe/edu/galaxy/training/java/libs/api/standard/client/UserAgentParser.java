package pe.edu.galaxy.training.java.libs.api.standard.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase utilitaria para parsear cadenas User-Agent y extraer información del cliente.
 * <p>
 * Usa patrones regex compilados como constantes estáticas (thread-safe).
 * Para User-Agent nulo o vacío, retorna ClientInfo con todos los campos UNKNOWN
 * y versiones vacías.
 */
public final class UserAgentParser {

    /**
     * Constructor privado para evitar instanciación (clase utilitaria).
     */
    private UserAgentParser() {
    }

    // --- Patrones de navegador ---

    /** Patrón para detectar Google Chrome. */
    private static final Pattern CHROME_PATTERN = Pattern.compile("Chrome/([\\d.]+)");

    /** Patrón para detectar Mozilla Firefox. */
    private static final Pattern FIREFOX_PATTERN = Pattern.compile("Firefox/([\\d.]+)");

    /** Patrón para detectar Apple Safari. */
    private static final Pattern SAFARI_PATTERN = Pattern.compile("Version/([\\d.]+).*Safari");

    /** Patrón para detectar Microsoft Edge. */
    private static final Pattern EDGE_PATTERN = Pattern.compile("Edg/([\\d.]+)");

    /** Patrón para detectar Opera. */
    private static final Pattern OPERA_PATTERN = Pattern.compile("OPR/([\\d.]+)");

    /** Patrón para detectar Internet Explorer. */
    private static final Pattern IE_PATTERN = Pattern.compile("MSIE ([\\d.]+)|Trident.*rv:([\\d.]+)");

    /** Patrón para detectar Samsung Internet. */
    private static final Pattern SAMSUNG_PATTERN = Pattern.compile("SamsungBrowser/([\\d.]+)");

    // --- Patrones de sistema operativo ---

    /** Patrón para detectar la versión de Windows. */
    private static final Pattern WINDOWS_VERSION_PATTERN = Pattern.compile("Windows NT ([\\d.]+)");

    /** Patrón para detectar la versión de macOS. */
    private static final Pattern MAC_VERSION_PATTERN = Pattern.compile("Mac OS X ([\\d_.]+)");

    /** Patrón para detectar la versión de Android. */
    private static final Pattern ANDROID_VERSION_PATTERN = Pattern.compile("Android ([\\d.]+)");

    /** Patrón para detectar la versión de iOS. */
    private static final Pattern IOS_VERSION_PATTERN = Pattern.compile("OS ([\\d_]+) like Mac OS X");

    /** Patrón para detectar la versión de Chrome OS. */
    private static final Pattern CROS_VERSION_PATTERN = Pattern.compile("CrOS [^ ]+ ([\\d.]+)");

    // --- Patrón de bot ---

    /** Patrón para detectar bots y rastreadores web conocidos. */
    private static final Pattern BOT_PATTERN = Pattern.compile(
            "(?i)(Googlebot|bingbot|Slurp|DuckDuckBot|Baiduspider|YandexBot|Sogou|facebookexternalhit|ia_archiver)");

    /**
     * Analiza una cadena User-Agent y retorna ClientInfo con los datos extraídos.
     *
     * @param userAgent cadena User-Agent (puede ser null o vacía)
     * @return ClientInfo con datos parseados o valores UNKNOWN
     */
    public static ClientInfo parse(String userAgent) {
        if (userAgent == null || userAgent.isEmpty()) {
            return ClientInfo.builder()
                    .userAgent("")
                    .browser(Browser.UNKNOWN)
                    .browserVersion("")
                    .operatingSystem(OperatingSystem.UNKNOWN)
                    .osVersion("")
                    .deviceType(DeviceType.UNKNOWN)
                    .ipAddress("")
                    .build();
        }

        Browser browser = detectBrowser(userAgent);
        String browserVersion = detectBrowserVersion(userAgent, browser);
        OperatingSystem os = detectOperatingSystem(userAgent);
        String osVersion = detectOsVersion(userAgent, os);
        DeviceType deviceType = detectDeviceType(userAgent);

        return ClientInfo.builder()
                .userAgent(userAgent)
                .browser(browser)
                .browserVersion(browserVersion)
                .operatingSystem(os)
                .osVersion(osVersion)
                .deviceType(deviceType)
                .ipAddress("")
                .build();
    }

    /**
     * Detecta el navegador a partir de la cadena User-Agent.
     * El orden de detección es importante: más específico primero.
     *
     * @param ua cadena User-Agent a analizar
     * @return navegador detectado o {@link Browser#UNKNOWN}
     */
    static Browser detectBrowser(String ua) {
        if (ua.contains("Edg/")) return Browser.EDGE;
        if (ua.contains("OPR/")) return Browser.OPERA;
        if (ua.contains("SamsungBrowser/")) return Browser.SAMSUNG_INTERNET;
        if (ua.contains("Chrome/") && !ua.contains("Edg/") && !ua.contains("OPR/")) return Browser.CHROME;
        if (ua.contains("Firefox/")) return Browser.FIREFOX;
        if (ua.contains("Safari") && !ua.contains("Chrome/") && !ua.contains("Edg/") && !ua.contains("OPR/")) return Browser.SAFARI;
        if (ua.contains("MSIE") || ua.contains("Trident")) return Browser.IE;
        return Browser.UNKNOWN;
    }

    /**
     * Detecta la versión del navegador a partir de la cadena User-Agent y el navegador detectado.
     *
     * @param ua      cadena User-Agent a analizar
     * @param browser navegador detectado previamente
     * @return versión del navegador o cadena vacía si no se detecta
     */
    static String detectBrowserVersion(String ua, Browser browser) {
        Pattern pattern = switch (browser) {
            case CHROME -> CHROME_PATTERN;
            case FIREFOX -> FIREFOX_PATTERN;
            case SAFARI -> SAFARI_PATTERN;
            case EDGE -> EDGE_PATTERN;
            case OPERA -> OPERA_PATTERN;
            case IE -> IE_PATTERN;
            case SAMSUNG_INTERNET -> SAMSUNG_PATTERN;
            case UNKNOWN -> null;
        };

        if (pattern == null) return "";

        Matcher matcher = pattern.matcher(ua);
        if (matcher.find()) {
            // El patrón de IE tiene dos grupos — se intenta el grupo 1 primero, luego el grupo 2
            if (browser == Browser.IE) {
                String version = matcher.group(1);
                return version != null ? version : (matcher.group(2) != null ? matcher.group(2) : "");
            }
            return matcher.group(1) != null ? matcher.group(1) : "";
        }
        return "";
    }

    /**
     * Detecta el sistema operativo a partir de la cadena User-Agent.
     *
     * @param ua cadena User-Agent a analizar
     * @return sistema operativo detectado o {@link OperatingSystem#UNKNOWN}
     */
    static OperatingSystem detectOperatingSystem(String ua) {
        if (ua.contains("Android")) return OperatingSystem.ANDROID;
        if (ua.contains("iPhone") || ua.contains("iPad") || ua.contains("iPod")) return OperatingSystem.IOS;
        if (ua.contains("CrOS")) return OperatingSystem.CHROME_OS;
        if (ua.contains("Windows")) return OperatingSystem.WINDOWS;
        if (ua.contains("Macintosh") || ua.contains("Mac OS")) return OperatingSystem.MACOS;
        if (ua.contains("Linux")) return OperatingSystem.LINUX;
        return OperatingSystem.UNKNOWN;
    }

    /**
     * Detecta la versión del sistema operativo a partir de la cadena User-Agent y el SO detectado.
     *
     * @param ua cadena User-Agent a analizar
     * @param os sistema operativo detectado previamente
     * @return versión del sistema operativo o cadena vacía si no se detecta
     */
    static String detectOsVersion(String ua, OperatingSystem os) {
        Pattern pattern = switch (os) {
            case WINDOWS -> WINDOWS_VERSION_PATTERN;
            case MACOS -> MAC_VERSION_PATTERN;
            case ANDROID -> ANDROID_VERSION_PATTERN;
            case IOS -> IOS_VERSION_PATTERN;
            case CHROME_OS -> CROS_VERSION_PATTERN;
            case LINUX, UNKNOWN -> null;
        };

        if (pattern == null) return "";

        Matcher matcher = pattern.matcher(ua);
        if (matcher.find()) {
            String version = matcher.group(1);
            return version != null ? version.replace('_', '.') : "";
        }
        return "";
    }

    /**
     * Detecta el tipo de dispositivo a partir de la cadena User-Agent.
     * Orden: BOT primero, luego TABLET, MOBILE, DESKTOP por defecto.
     *
     * @param ua cadena User-Agent a analizar
     * @return tipo de dispositivo detectado o {@link DeviceType#DESKTOP} por defecto
     */
    static DeviceType detectDeviceType(String ua) {
        // Detección de BOT primero
        if (BOT_PATTERN.matcher(ua).find()) return DeviceType.BOT;

        // Detección de TABLET
        if (ua.contains("iPad") || ua.contains("Tablet")
                || (ua.contains("Android") && !ua.contains("Mobile"))) {
            return DeviceType.TABLET;
        }

        // Detección de MOBILE
        if (ua.contains("Mobile") || ua.contains("iPhone") || ua.contains("iPod")
                || Pattern.compile("Android.*Mobile").matcher(ua).find()) {
            return DeviceType.MOBILE;
        }

        // DESKTOP es el valor por defecto para no-mobile, no-tablet, no-bot
        return DeviceType.DESKTOP;
    }
}
