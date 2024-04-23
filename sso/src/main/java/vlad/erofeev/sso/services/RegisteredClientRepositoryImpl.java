package vlad.erofeev.sso.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final List<RegisteredClient> registeredClients = List.of(
            RegisteredClient.withId("client")
                    .clientId("client")
                    .clientSecret(passwordEncoder.encode("secret"))
                    .scope("client")
                    .redirectUri("http://localhost:3000/login")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                            .idTokenSignatureAlgorithm(SignatureAlgorithm.ES256)
                            .accessTokenTimeToLive(Duration.ofMinutes(30))
                            .reuseRefreshTokens(false)
                            .refreshTokenTimeToLive(Duration.ofDays(7)).build()).build(),
            RegisteredClient.withId("resource")
                    .clientId("resource")
                    .scope("service")
                    .clientSecret(passwordEncoder.encode("secret"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenTimeToLive(Duration.ofHours(1)).build())
                    .build()
    );

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return registeredClients.stream().filter(client -> client.getId().equals(id)).findFirst().get();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return registeredClients.stream().filter(client -> client.getClientId().equals(clientId)).findFirst().get();
    }
}
