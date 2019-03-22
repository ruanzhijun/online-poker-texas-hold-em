package es.msanchez.poker.server.starter;

import es.msanchez.poker.server.network.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
@SpringBootApplication
@ComponentScan("es.msanchez.poker.server.config")
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
                    final Menu menu = context.getBean(Menu.class);
                    final Runnable menuRunnable = menu::selector; // New Thread where it does its operations.
                    final Thread t = new Thread(menuRunnable);
                    t.start();
                    log.info("Stated new thread '{}'", t);
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        };

    }

}
