package ro.cpatrut.auth.services.converters;

import com.google.common.collect.Maps;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;
import ro.cpatrut.auth.dto.ClientTO;

import java.util.Map;

public class ClientTOConverter implements Converter<ClientTO, BaseClientDetails> {


    public static final String ADDITIONAL_INFORMATION_KEY = "additional: ";

    @Override
    public BaseClientDetails convert(final MappingContext<ClientTO, BaseClientDetails> context) {
        final ClientTO client = context.getSource();
        final BaseClientDetails baseClientDetails = new BaseClientDetails(client.getClientId(),
                client.getResourcedIds(),
                client.getScope(),
                client.getAuthorizedGrantTypes(),
                client.getAuthorities(),
                client.getWebServerRedirectUri()
        );

        baseClientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
        baseClientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
        baseClientDetails.setClientSecret(client.getClientSecret());
        baseClientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(client.getAutoApproveScopes()));
        baseClientDetails.setAdditionalInformation(extractAdditionalInformation(client));
        return baseClientDetails;
    }

    private Map<String, String> extractAdditionalInformation(final ClientTO client) {
        final Map<String, String> additionalInfo = Maps.newHashMap();
        additionalInfo.put(ADDITIONAL_INFORMATION_KEY, client.getAdditionalInformation());
        return additionalInfo;
    }


}
