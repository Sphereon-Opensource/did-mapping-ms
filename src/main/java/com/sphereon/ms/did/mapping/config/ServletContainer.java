package com.sphereon.ms.did.mapping.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        value = "tomcat.protocol.ajp",
        havingValue = "true"
)
public class ServletContainer {

    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return container -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                ((TomcatEmbeddedServletContainerFactory) container)
                        .setProtocol("AJP/1.3");
            }
        };
    }
}
