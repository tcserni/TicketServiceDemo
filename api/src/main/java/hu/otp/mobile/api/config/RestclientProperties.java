package hu.otp.mobile.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "restclient")
public class RestclientProperties {

	private String ticketUrl;
	private String coreUrl;

	public String getTicketUrl() {
		return ticketUrl;
	}

	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}

	public String getCoreUrl() {
		return coreUrl;
	}

	public void setCoreUrl(String coreUrl) {
		this.coreUrl = coreUrl;
	}

}
