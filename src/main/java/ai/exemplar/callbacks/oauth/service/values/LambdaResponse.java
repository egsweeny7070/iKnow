package ai.exemplar.callbacks.oauth.service.values;

import java.util.Map;

public class LambdaResponse {

    private Boolean isBase64Encoded;

    private Integer statusCode;

    private Map<String, String> headers;

    private String body;

    public LambdaResponse(Boolean isBase64Encoded, Integer statusCode, Map<String, String> headers, String body) {
        this.isBase64Encoded = isBase64Encoded;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public Boolean getBase64Encoded() {
        return isBase64Encoded;
    }

    public void setBase64Encoded(Boolean base64Encoded) {
        isBase64Encoded = base64Encoded;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
