package com.sie.saaf.common.configration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhangJun
 * @createTime 2018-11-05 4:29 PM
 * @description
 */
@ConfigurationProperties(prefix = "fastdfs")
@Component
public class FastdfsConfigration {
	private Integer connectTimeoutInSeconds;
	private Integer networkTimeoutInSeconds;
	private String charset;
	private Boolean httpAntiStealToken;
	private String httpSecretKey;
	private String httpTrackerHttpPort;
	private List<String> trackerServers;

	private final ThumbImage thumbImage = new ThumbImage();
	private final Access access = new Access();


	public Integer getConnectTimeoutInSeconds() {
		return connectTimeoutInSeconds;
	}

	public void setConnectTimeoutInSeconds(Integer connectTimeoutInSeconds) {
		this.connectTimeoutInSeconds = connectTimeoutInSeconds;
	}

	public Integer getNetworkTimeoutInSeconds() {
		return networkTimeoutInSeconds;
	}

	public void setNetworkTimeoutInSeconds(Integer networkTimeoutInSeconds) {
		this.networkTimeoutInSeconds = networkTimeoutInSeconds;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Boolean getHttpAntiStealToken() {
		return httpAntiStealToken;
	}

	public void setHttpAntiStealToken(Boolean httpAntiStealToken) {
		this.httpAntiStealToken = httpAntiStealToken;
	}

	public String getHttpSecretKey() {
		return httpSecretKey;
	}

	public void setHttpSecretKey(String httpSecretKey) {
		this.httpSecretKey = httpSecretKey;
	}

	public String getHttpTrackerHttpPort() {
		return httpTrackerHttpPort;
	}

	public void setHttpTrackerHttpPort(String httpTrackerHttpPort) {
		this.httpTrackerHttpPort = httpTrackerHttpPort;
	}

	public List<String> getTrackerServers() {
		return trackerServers;
	}

	public void setTrackerServers(List<String> trackerServers) {
		this.trackerServers = trackerServers;
	}

	public ThumbImage getThumbImage() {
		return thumbImage;
	}

	public Access getAccess() {
		return access;
	}

	public static class ThumbImage {
		private String width;
		private String height;

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}
	}

	public static class Access {
		private String address;
		private String httpType;
		private String httpPort;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getHttpType() {
			return httpType;
		}

		public void setHttpType(String httpType) {
			this.httpType = httpType;
		}

		public String getHttpPort() {
			return httpPort;
		}

		public void setHttpPort(String httpPort) {
			this.httpPort = httpPort;
		}
	}
}
