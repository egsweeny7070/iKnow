package ai.exemplar.api.auth0;

public interface Auth0ApiProvider {

    String TENANT_NAME = "exemplar";

    String AUDIENCE_NAME = "https://api.exemplar.ai/";

    String email(String token);
}
