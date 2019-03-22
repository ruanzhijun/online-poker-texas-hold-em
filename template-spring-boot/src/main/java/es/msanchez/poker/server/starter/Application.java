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

@Slf4j
@SpringBootApplication
public class Application {

    private static final int PORT = 8143; // TODO: move to configurable properties.

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext context) {
        return args -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                System.out.println("Server started and ready.");
                while (true) {
                    final Socket socket = serverSocket.accept();
                    Connection.open(socket);
                    Runnable menu = Menu::selector; // New Thread where it does its operations.
                    new Thread(menu).start();
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        };

    }

}
