package ro.cpatrut.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ro.cpatrut.auth.dto.validation.SupportedAuthorities;
import ro.cpatrut.auth.dto.validation.SupportedGrantTypes;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static ro.cpatrut.auth.dto.PasswordRestrictions.ERROR_MESSAGE_PASSWORD;
import static ro.cpatrut.auth.dto.PasswordRestrictions.REGEX_ENFORCER;

@ApiModel(description = "Transfer object that encapsulates client information")
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientTO {

    private static final int ONE_WEEk_VALIDITY = 604800;
    private static final int ONE_DAY_VALIDITY = 93600;

    @ApiModelProperty(value = "Client identifier, example: my-trusted-client")
    @NotNull
    @Size(min = 5, max = 255)
    private final String clientId;

    @ApiModelProperty(position = 1, value = "Not important, they can be null")
    private final String resourcedIds;

    @ApiModelProperty(position = 2, value = ERROR_MESSAGE_PASSWORD)
    @NotNull
    @Size(min = 8, max = 255)
    @Pattern(regexp = REGEX_ENFORCER, message = ERROR_MESSAGE_PASSWORD)
    private final String clientSecret;

    @ApiModelProperty(position = 3, value = "Comma separated values, example:  read, write")
    @NotNull
    @Size(min = 4, max = 255)
    private final String scope;

    @ApiModelProperty(position = 4, value = "password, refresh_token  & others, see oauth2 standard")
    @NotNull
    @SupportedGrantTypes
    @Size(min = 5, max = 255)
    private final String authorizedGrantTypes;

    @ApiModelProperty(position = 5, value = "can be null")
    private final String webServerRedirectUri;

    @ApiModelProperty(position = 6, value = "can be null")
    @NotNull
    @Size(min = 5, max = 255)
    @SupportedAuthorities
    private final String authorities;

    @ApiModelProperty(position = 7)
    @NotNull
    @Min(ONE_DAY_VALIDITY)
    private final int accessTokenValidity;

    @ApiModelProperty(position = 8)
    @NotNull
    @Min(ONE_WEEk_VALIDITY)
    private final int refreshTokenValidity;

    @ApiModelProperty(position = 9)
    private final String additionalInformation;

    @ApiModelProperty(position = 10)
    private final String autoApproveScopes;

    @Builder
    private static ClientTO createNewUserTO(final String clientId,
                                            final String resourcedIds,
                                            final String clientSecret,
                                            final String scope,
                                            final String authorizedGrantTypes,
                                            final String webServerRedirectUri,
                                            final String authorities,
                                            final int accessTokenValidity,
                                            final int refreshTokenValidity,
                                            final String additionalInformation,
                                            final String autoApproveScopes) {
        return new ClientTO(clientId,
                resourcedIds,
                clientSecret,
                scope,
                authorizedGrantTypes,
                webServerRedirectUri,
                authorities,
                accessTokenValidity,
                refreshTokenValidity,
                additionalInformation,
                autoApproveScopes);
    }
}
