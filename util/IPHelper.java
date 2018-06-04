package fgafa.util;

import java.io.File;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HostAndPort;
import com.integrien.alive.common.util.CommonConstants;

public class IPHelper {

    private static final Logger logger = LoggerFactory.getLogger(IPHelper.class);

    private static String PROP_BIND_ADDRESS = "bind-address";

    private static PropertiesConfiguration gemfireNativeProperties = new PropertiesConfiguration();

    /**
     * Tests if a string is a valid IPv4 or IPv6 address.
     * @param ip    IP address to validate
     * @return      true if valid address, false otherwise
     */
    public static boolean isIPAddress(String ip)
    {
        if (ip == null) {
            return false;
        }
        return InetAddressValidator.getInstance().isValid(ip);
    }

    /**
     * Tests if a string is a valid IPv4 address.
     * @param ip    IP address to validate
     * @return      true if valid address, false otherwise
     */
    public static boolean isIPv4Address(String ip)
    {
        if (ip == null) {
            return false;
        }
        return InetAddressValidator.getInstance().isValidInet4Address(ip);
    }

    /**
     * Tests if a string is a valid IPv6 address.
     * @param ip    IP address to validate
     * @return      true if valid address, false otherwise
     */
    public static boolean isIPv6Address(String ip)
    {
        if (ip == null || "".equals(ip)) {
            return false;
        }
        return InetAddressValidator.getInstance().isValidInet6Address(ip);
    }

    /**
     * Trims and removes the leading and trailing brackets ([]) from a string.
     * @param ipAddress
     * @return              If the ipAddress contains a leading and trailing bracket, the contained text
     *                      will be returned.  Otherwise the original text will be returned. Note: Leading and
     *                      trailing whitespace will also be removed.
     */
    public static String removeBrackets(String ipAddress) {
        String trimmedIPAddress = ipAddress.trim();

        Pattern p = Pattern.compile("^\\[(.*)\\]$");
        Matcher m = p.matcher(trimmedIPAddress);
        if (m.matches()) {
            return m.group(1);
        }

        return trimmedIPAddress;
    }

    /**
     * Tests if a string is a valid IP address.  The brackets around an IPv6 address are allowed.
     * @param ipAddress
     * @return  true if the ipAddress is valid, false otherwise.
     */
    public static boolean isIPAddressAllowBrackets(String ipAddress)
    {
        if (ipAddress == null) {
            return false;
        }

        String cleanIPAddress = ipAddress.trim();

        if (isIPAddress(cleanIPAddress)) {
            return true;
        } else {
            return isIPv6Address(removeBrackets(cleanIPAddress));
        }
    }

    /**
     * Will trim an ipAddress and place it in brackets if it is an IPv6 address
     * <p>
     * Note: The validity of the address is never tested.
     * </p>
     * @param ipAddress address to clean
     * @return          one of the following will be returned depending on ipAddress:
     *                      null         - ""
     *                      IPv6 Address without brackets - trimmed IPv6 address with brackets
     *                      Anything else - trimmed version of ipAddress
     */
    public static String cleanIPAddressWithIPv6Brackets(String ipAddress)
    {
        if (ipAddress == null) {
            return "";
        }

        String cleanIPAddress = ipAddress.trim();

        if (isIPv6Address(cleanIPAddress)) {
            return "[" + cleanIPAddress + "]";
        }

        return cleanIPAddress;
    }

    /**
     * Will trim an ipAddress and remove any surrounding brackets if it is an IPv6 address
     * <p>
     * Note: The validity of the address is never tested.
     * </p>
     * @param ipAddress address to clean
     * @return          one of the following will be returned depending on ipAddress:
     *                      null         - ""
     *                      IPv6 Address with brackets - trimmed IPv6 address without brackets
     *                      Anything else - trimmed version of ipAddress
     */
    public static String cleanIPAddressWithoutIPv6Brackets(String ipAddress)
    {
        if (ipAddress == null) {
            return "";
        }

        String cleanIPAddress = ipAddress.trim();

        String bracketlessIP = removeBrackets(cleanIPAddress);
        if (isIPv6Address(bracketlessIP)) {
            return bracketlessIP;
        }

        return cleanIPAddress;
    }

    /**
     * Determine the address of the local system where this code is running.
     *
     * @return The public FQDN, IPv6 address, or IPv4 address of the local system
     */
    public static InetAddress determineLocalSystemInetAddress() {

        InetAddress inetAddress = null;
        String address = determineLocalSystemAddress();
        try {
            inetAddress = InetAddress.getByName(address);
            logger.debug("Created InetAddress from address {}", inetAddress.getCanonicalHostName());
        }
        catch(UnknownHostException e) {
            logger.warn("Local system host address unknown");
            logger.info("", e);
            inetAddress = null;
        }

        return inetAddress;
    }

    /**
     * Determines the address of the local Node used for Ops communication.
     *
     * @return the address of the local Node used for Ops communication.
     */
    public static String determineLocalSystemAddress() {

        String address = getGemfireNativeProperty(PROP_BIND_ADDRESS);
        logger.info("gemfire.native.properties bind address: {}", address);
        if (address == null || address.isEmpty()) {
            InetAddress inetAddress = scanNicsForFirstInetAddress(IPVersion.IPV4);
            address = inetAddress.getCanonicalHostName();
            logger.warn("gemfire.native.properties bind-address was empty; 1st NIC returned address {}", address);
        }

        if (address == null || address.isEmpty()) {
            logger.warn("Unable to determine local system address; using localhost");
            address = "localhost";
        }

        return address;
    }

    /**
     * Given host and port return them in a format compatible with {@link URI} creation. This will properly handle
     * bracketing IPv6 addresses. If port is {@code null}, it will not be used in construction of the returned String.
     *
     * @param host The host address. It can be FQDN, IPv4, or IPv6 (with or without brackets).
     * @param port The port or {@code null}.
     * @return A String that can be used in URI construction.
     */
    public static String format(final String host, final String port) {
        // HostAndPort.toString() will always add brackets to IPv6 addresses.
        if (StringUtils.isBlank(port)) {
            // HostAndPort.fromString(String) allows no port to be specified. With no port, it will accept an IPv6
            // address with or without brackets.
            return HostAndPort.fromString(host).toString();
        }

        // By default, HostAndPort.fromParts(String, int) accepts IPv6 address with or without brackets.
        return HostAndPort.fromParts(host, Integer.parseInt(port)).toString();
    }

    /**
     * Given a host return it in a format compatible with {@link URI} creation. This will properly handle
     * bracketing IPv6 addresses.
     *
     * @param host The host address. It can be FQDN, IPv4, or IPv6 (with or without brackets).
     * @return A String that can be used in URI construction.
     */
    public static String format(final String host) {
        return format(host, null);
    }

    /**
     * Determine whether the Ops application has been configured with IPv6.
     *
     * @return true if the Ops application is configured to run using IPv6
     */
    public static boolean isOpsIPv6() {

        return isIPv6Address(getGemfireNativeProperty(PROP_BIND_ADDRESS));
    }

    /**
     * Chooses the NIC for configuring the address of the current Node.
     * Takes into account whether the cluster is IPv4 or IPv6.
     *
     * @return address to use to configure the Node
     */
    public static InetAddress chooseInetAddressForOpsConfig() {

        if (isOpsIPv6()) {
            return scanNicsForFirstInetAddress(IPVersion.IPV6);
        }
        else{
            return scanNicsForFirstInetAddress(IPVersion.IPV4);
        }
    }

    /**
     * Enum indicating IPv4 or IPv6
     */
    private enum IPVersion {
        IPV4, IPV6
    }

    private static InetAddress scanNicsForFirstInetAddress(IPVersion ipVersion) {

        try {
            Enumeration<NetworkInterface> networkInterfaces;
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {

                NetworkInterface currentInterface = networkInterfaces.nextElement();
                if (currentInterface.isUp() && !currentInterface.isLoopback() && !currentInterface.isVirtual()) {

                    for (final InetAddress inetAddress : Collections.list(currentInterface.getInetAddresses())) {

                        if (ipVersion == IPVersion.IPV6 && !(inetAddress instanceof Inet6Address)) {
                            continue;
                        }
                        if (ipVersion == IPVersion.IPV4 && !(inetAddress instanceof Inet4Address)) {
                            continue;
                        }

                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                            return inetAddress;
                        }
                    }
                }
            }
        }
        catch(SocketException e) {
            logger.warn("SocketException scanning NICs for IP address");
            logger.info("", e);
        }

        return null;
    }

    /**
     * A simple, fast check to see if the ipAddress refers to the
     * localhost, either by name or IP address.
     *
     * Don't use InetAddress.getByName() since it performs DNS lookup
     *
     * @param address FQDN or IP address
     * @return true if the address refers to "home"
     */
    public static boolean isLocalhost(String address) {

        String workingAddress = address.trim().toLowerCase();
        return workingAddress.startsWith("localhost")
                 || workingAddress.equals("127.0.0.1")
                 || workingAddress.equals("::1");
    }

    private static String getGemfireNativeProperty(String key) {

        return getGemfireNativeProperties().getString(key);
    }

    private static PropertiesConfiguration getGemfireNativeProperties() {

        if (gemfireNativeProperties.isEmpty()) {
            String aliveBase = System.getProperty(CommonConstants.ALIVE_BASE);
            if (aliveBase != null) {
                String gemfireNativeProps = aliveBase + File.separator + "user" + File.separator + "conf" + File.separator + "gemfire.native.properties";
                try {
                    logger.info("Load gemfire native properties from file: " + gemfireNativeProps);
                    gemfireNativeProperties.load(new File(gemfireNativeProps));
                    gemfireNativeProperties.setReloadingStrategy(new FileChangedReloadingStrategy());
                } catch (Exception e) {
                    logger.error("Error loading GemFire native properties", e);
                }
            }
            else {
                logger.warn("ALIVE_BASE is null");
            }
        }

        return gemfireNativeProperties;
    }
}
