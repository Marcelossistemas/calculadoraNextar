package br.com.marcelos.desafio.jmotivation.nex.calculadoranex;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * Classe Principal de execução do Projeto Spring-boot
 */
//@SpringBootApplication
//@ComponentScan(basePackages = "br.com.marcelos.desafio.jmotivation.nex.calculadoranex.*")
@SpringBootApplication(scanBasePackages = "br.com.marcelos.desafio.jmotivation.nex.calculadoranex")
public class CalculadonexApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculadonexApplication.class, args);
	}

}
