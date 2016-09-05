package it.unisalento.wox;

public class AsyncTaskParams {

	private String url;
	private String method;
	private String xmlPayload;
	public static final String GET = "get";
	public static final String POST = "post";
	
	public AsyncTaskParams(String url, String method, String xmlPayload) {
		super();
		this.url = url;
		this.method = method;
		this.xmlPayload = xmlPayload;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getXmlPayload() {
		return xmlPayload;
	}
	public void setXmlPayload(String xmlPayload) {
		this.xmlPayload = xmlPayload;
	}
}
