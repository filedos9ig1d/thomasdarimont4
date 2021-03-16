package demo;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;

@Component
class KeycloakEvaluationContextExtension implements EvaluationContextExtension {

    @Override
    public String getExtensionId() {
        return "keycloak";
    }

    @Override
    public Object getRootObject() {
        return getKeycloakSecurityContext();
    }

    protected KeycloakSecurityContext getKeycloakSecurityContext() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Principal principal = attributes.getRequest().getUserPrincipal();

        if (principal instanceof KeycloakPrincipal) {
            return KeycloakPrincipal.class.cast(principal).getKeycloakSecurityContext();
        }

        return null;
    }
}
