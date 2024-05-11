package roomescape.global.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import roomescape.auth.provider.model.TokenProvider;
import roomescape.auth.resolver.TokenResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String TIME_FORMAT = "HH:mm";

    private final TokenProvider tokenProvider;

    @Autowired
    public WebMvcConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer localTimeSerializerCustomizer() {
        return builder -> builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenResolver(tokenProvider));
    }
}