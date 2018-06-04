package fgafa.util;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class Dnslookup {
	private static final String MX_ATTRIB = "MX";
	private static final String ADDR_ATTRIB = "A";
	private static String[] MX_ATTRIBS = {MX_ATTRIB};
	private static String[] ADDR_ATTRIBS = {ADDR_ATTRIB};
	 
	private InitialDirContext idc;
	 
	public Dnslookup() throws NamingException {
		this(null);
	}

	public Dnslookup(String dnsServer) throws NamingException {
		Properties env = new Properties();
		
		if(null != dnsServer){
			env.put(Context.PROVIDER_URL, "dns://ns1.my.domain");			
		}//else use DNS servers defined on the host
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
		idc = new InitialDirContext(env);
	}
	
	public List<String> getMXServers(String domain) throws NamingException {
	 
	  List<String> servers = new ArrayList<>();
	  Attributes attrs = idc.getAttributes(domain, MX_ATTRIBS);
	  Attribute attr = attrs.get(MX_ATTRIB);
	 
	  if (attr != null) {
	    for (int i = 0; i < attr.size(); i++) {
	      String mxAttr = (String) attr.get(i);
	      String[] parts = mxAttr.split(" ");
	 
	      // Split off the priority, and take the last field
	      servers.add(parts[parts.length - 1]);
	    }
	  }
	 
	  return servers;
	}
	
	public List<String> getIPAddresses(String hostname) throws NamingException {

		List<String> ipAddresses = new ArrayList<>();
		Attributes attrs = idc.getAttributes(hostname, ADDR_ATTRIBS);
		Attribute attr = attrs.get(ADDR_ATTRIB);

		if (attr != null) {
			for (int i = 0; i < attr.size(); i++) {
				ipAddresses.add((String) attr.get(i));
			}
		}

		return ipAddresses;
	}
	
	public String getRevName(String ipAddr) throws NamingException {

		String revName = null;
		String[] quads = ipAddr.split("\\.");

		// StringBuilder would be better, I know.
		ipAddr = "";

		for (int i = quads.length - 1; i >= 0; i--) {
			ipAddr += quads[i] + ".";
		}

		ipAddr += "in-addr.arpa.";
		Attributes attrs = idc.getAttributes(ipAddr, new String[] { "PTR" });
		Attribute attr = attrs.get("PTR");

		if (attr != null) {
			revName = (String) attr.get(0);
		}

		return revName;
	}
	
	public String getHostName(String ipAddress) throws UnknownHostException{
		return InetAddress.getByName(ipAddress).getCanonicalHostName();
	}
	public String getHostName() throws UnknownHostException{
		return InetAddress.getLocalHost().getCanonicalHostName();
	}
	
	public static void main(String[] args){
		
		System.out.println("--start--");
		
		String domain_remote = "prme-vcops-001-vm1.eng.vmware.com";
		String domain_local = "prome-1s-dhcp293.eng.vmware.com"; //  //joeyz-mac.eng.vmware.com
		
		String ip_remote = "10.20.24.22";
		String ip_local = "10.20.133.58";
		
		try {
			Dnslookup sv = new Dnslookup();
			
			System.out.println("--getHostName--" + sv.getHostName());
			System.out.println("--getHostName--" + sv.getHostName(ip_local));
			System.out.println("--getHostName--" + sv.getHostName(ip_remote));

			System.out.println("--getRevName--" + sv.getRevName(ip_local));
			System.out.println("--getRevName--" + sv.getRevName(ip_remote));
			
			System.out.println("--getMXServers--" + sv.getMXServers(domain_local));
			System.out.println("--getMXServers--" + sv.getMXServers(domain_remote));
			
			System.out.println("--getIPAddresses--" + sv.getIPAddresses(domain_local));
			System.out.println("--getIPAddresses--" + sv.getIPAddresses(domain_remote));			
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("--end--");
	}
}