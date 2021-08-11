package hu.otp.mobile.ticket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "restclient")
public class RestclientProperties {

	private String partnerUrl;
	private String coreUrl;

	public String getPartnerUrl() {
		return partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {
		this.partnerUrl = partnerUrl;
	}

	public String getCoreUrl() {
		return coreUrl;
	}

	public void setCoreUrl(String coreUrl) {
		this.coreUrl = coreUrl;
	}

}
