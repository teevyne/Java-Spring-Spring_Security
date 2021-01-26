package com.iwiseoauth.springbootoauthjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class ConfigOAuth2 extends AuthorizationServerConfigurerAdapter {

    private String clientId = "client-developer";
    private String clientSecret = "client-developer-secret-key";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpQIBAAKCAQEAzaoaBkYfi2YmYWzqmDa+9iLl72u4+lQkJQ8Tj8nKJkguloOe\n" +
            "S7ZXiJmnQqaLFqCgzTaNwm1KsAXs6SW7CP1oOFuAltphMh47nRvqWQxAt0IzeU2t\n" +
            "YcsWPLumCzXl0JLEPB+eFZG82SC0iJbcFXWGp4MdJSx4QyM6uv6yosTPuGZ+OTDT\n" +
            "pygT5vTX45TEcz62otzSQG/fkhb32hkKxVZhe0Q2bJcPz0R4HDtPdJ3CRT1pG3FI\n" +
            "HHi1vNYx8wp5+APzSDEp/qqAhXGo2y7BhPRsC6pqKgJ9+uN4GHwWoyg2XUTwfZN7\n" +
            "RH2RbOR4NL5zhJYDtNTNOdyLfwsu/vWLrX1tUQIDAQABAoIBAC9q1pE3vbIgHR09\n" +
            "RWFOzfxl02m+BmoA2EyLC1GNjsQAxG0Db425PrlJ49xkFZFj+28U8t4u4Jdw5kxi\n" +
            "3JqYAl1sC1u8gchSXCA+/WsGfi1CYCfJlv4DGt9pzECtbo0GKaRLDeGfNlFBTx5m\n" +
            "2dtAu4kHm8KkXB3uk23lpz4L7nBvFuD9uR6THanmjofbvo5BGEdS2ibjHnenjiLv\n" +
            "pkxUQOW2P37p+oscV448PrWLnRGX+1ssd3xWVqNNpSmc2W0b8o7tmaB9mxRGCb+o\n" +
            "YNRzbrcmH9J6MMeumGkBBxRwPXN6e0y3sRvQQKMsfRGXPtc8MNLX0OxOuq4SKomc\n" +
            "BEO1djkCgYEA+Yxfl6Ink+NqZFBkNXZpWWrBNpMhCIFM3P/h1xVmoFB9sB9tuJcI\n" +
            "UIMgSASb/4gDyfSQYqoJNJNVmllwllWC2Ad2GSrogqcwocTeci+/zEWYBVDAuImu\n" +
            "1aBW11af3AwhbXMutpAMn9SbzEl7KHjPB/P4+hSNS+IZ36HMDLKbBNMCgYEA0vtI\n" +
            "z+v9rv6xCfuVj++51zKQwcSZZc8wIr0/aj4Bn/6R+46iyw2xyqRkgmATVibTi2Cf\n" +
            "C/6NaIiuF3XI3rA0VDt50/ftCF/g5WB8SL7AndUaPSAcI0WBNnwv9BVp27paMFdc\n" +
            "+lGtfQG/kcIwaXdpddKr2QojyphalHV7ZdeUvssCgYEA1k7BAV3Wk2DoS4HmrSsl\n" +
            "yg6uI4ozAjTYp6pwbuAIJIE7j3dOKQo7r7kMjgy3U7mhV1zaaoEQKsCk8UsCL3j8\n" +
            "Rz4RJAf/acTfNO33AyUSmADkTxxMha5oITnzmjbgTn90baRPZ6VJ0jEqI0SvqdAm\n" +
            "K/RPy6S+u35PLgirTfRMwNUCgYEAn3d9FmL58M3jBHebGRsJ7WTz0vGFPFV4vZkr\n" +
            "GGNbmRGyR2ANHcrxCghtS7nQg7p/BIb4T/mL6mn8pBDLzb2DH/OAZr2q9Dx74QOn\n" +
            "jNhZADL1TSQ7AFHlk0gUernAtGrLBmTSCwW3SpgKsuIC84RlOsAMUdT/Ln8vX6zY\n" +
            "oyZp8DcCgYEA2m/v1W2Z/FOGlcl8mynQAi2qH0RsySF/Ey4Sc7P2Dx4JYS5jda3M\n" +
            "Myib3y4Rcbq11f+XftJpgOjFljabiWAjlQrLNZnXB2ygCdtf9pWmW13FgoCvnnRO\n" +
            "94q4OdXyrBuK0DvdWG6WXePlrAA9TcrAkVJ3UVx95nmLHgn4DW1h/bg=\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzaoaBkYfi2YmYWzqmDa+\n" +
            "9iLl72u4+lQkJQ8Tj8nKJkguloOeS7ZXiJmnQqaLFqCgzTaNwm1KsAXs6SW7CP1o\n" +
            "OFuAltphMh47nRvqWQxAt0IzeU2tYcsWPLumCzXl0JLEPB+eFZG82SC0iJbcFXWG\n" +
            "p4MdJSx4QyM6uv6yosTPuGZ+OTDTpygT5vTX45TEcz62otzSQG/fkhb32hkKxVZh\n" +
            "e0Q2bJcPz0R4HDtPdJ3CRT1pG3FIHHi1vNYx8wp5+APzSDEp/qqAhXGo2y7BhPRs\n" +
            "C6pqKgJ9+uN4GHwWoyg2XUTwfZN7RH2RbOR4NL5zhJYDtNTNOdyLfwsu/vWLrX1t\n" +
            "UQIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsersService usersService;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {

        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer())
                .authenticationManager(authenticationManager)
                .userDetailsService(usersService);

    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);
    }
}

//Expectations
//
//        The vid
//        All the grant types - as the media for consumption would differ
//        fetching the user ID from the principal
//
//
//        https://www.pixeltrice.com/spring-boot-security-using-oauth2-with-jwt/
//        https://dzone.com/articles/implementing-your-own-spring-boot-oauth2-authoriza
//        https://medium.com/swlh/spring-oauth-2-9b0bfbf9a62a (read)