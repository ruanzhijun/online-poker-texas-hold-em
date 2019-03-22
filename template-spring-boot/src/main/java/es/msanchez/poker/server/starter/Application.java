package es.msanchez.poker.server.starter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import es.msanchez.poker.server.network.Connection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

import static es.msanchez.poker.server.config.Properties.PORT;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext context) {
        return args -> {
            try {
                final ServerSocket serverSocket = context.getBean(ServerSocket.class);
                log.info("Started new ServerSocket on port '{}'", serverSocket.getLocalPort());
                while (true) {
                    final Socket socket = serverSocket.accept();
                    Connection.open(socket);
                    final Runnable menu = Menu::selector; // New Thread where it does its operations.
                    final Thread t = new Thread(menu);
                    t.start();
                    log.info("Stated new thread '{}'", t);
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        };

    }

}
