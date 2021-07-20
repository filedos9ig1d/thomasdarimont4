package demo.support.keycloak;

import demo.config.KeycloakDataServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KeycloakAudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final OAuth2Error ERROR_INVALID_AUDIENCE = new OAuth2Error("invalid_token", "Invalid audience", null);

    private final KeycloakDataServiceProperties keycloakDataServiceProperties;

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {

        String authorizedParty = jwt.getClaimAsString("azp");

        if (!keycloakDataServiceProperties.getJwt().getAllowedAudiences().contains(authorizedParty)) {
            return OAuth2TokenValidatorResult.failure(ERROR_INVALID_AUDIENCE);
        }

        return OAuth2TokenValidatorResult.success();
    }
}
