package es.msanchez.poker.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

import static es.msanchez.poker.server.config.Properties.PORT;

@Configuration
@ComponentScan({"es.msanchez.poker.server.**"})
public class SpringConfig {

    @Bean
    public ServerSocket serverSocket() throws IOException {
        return new ServerSocket(PORT);
    }

}
