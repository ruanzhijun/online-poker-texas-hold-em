package es.msanchez.poker.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

import static es.msanchez.poker.server.config.Properties.PORT;

@Configuration
public class SpringConfig {

    @Bean
    public ServerSocket serverSocket() throws IOException {
        return new ServerSocket(PORT);
    }

}
