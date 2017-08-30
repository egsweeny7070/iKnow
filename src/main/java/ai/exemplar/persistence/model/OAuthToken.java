package ai.exemplar.persistence.model;

import java.time.LocalDateTime;

public class OAuthToken {

    private String username;

    private String provider;

    private String token;

    private String refreshToken;

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime expiration;

    private String internalId;

    public OAuthToken(String username, String provider, String token, String refreshToken, LocalDateTime created, LocalDateTime updated, LocalDateTime expiration, String internalId) {
        this.username = username;
        this.provider = provider;
        this.token = token;
        this.refreshToken = refreshToken;
        this.created = created;
        this.updated = updated;
        this.expiration = expiration;
        this.internalId = internalId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }
}
