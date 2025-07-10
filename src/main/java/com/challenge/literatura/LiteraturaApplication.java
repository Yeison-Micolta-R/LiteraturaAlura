package com.challenge.literatura;

import com.challenge.literatura.principal.Principal;
import com.challenge.literatura.repository.AutorRepository;
import com.challenge.literatura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication  implements CommandLineRunner {

	@Autowired
	private LibrosRepository librosRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(librosRepository,autorRepository);
		principal.muestraElMenu();

	}

}
