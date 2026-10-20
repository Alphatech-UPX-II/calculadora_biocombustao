package br.com.alphatech.agrowatt;

import org.springframework.boot.SpringApplication;

/** Sobe o AGROWATT com o banco H2 em memória (perfil test), sem precisar de MySQL. */
public class TestAgrowattApplication {

    public static void main(String[] args) {
        SpringApplication.from(AgrowattApplication::main)
                .withAdditionalProfiles("test")
                .run(args);
    }
}
