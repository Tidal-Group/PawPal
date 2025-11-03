package com.tidal.pawpal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PawpalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PawpalApplication.class, args);
	}

}

// package com.tidal.pawpal;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// // --- AGGIUNGI QUESTO IMPORT ---
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @SpringBootApplication
// // --- AGGIUNGI QUESTA ANNOTAZIONE ---
// @EnableJpaRepositories(basePackages = "com.tidal.pawpal.repositories")
// public class PawpalApplication {

//     public static void main(String[] args) {
//         SpringApplication.run(PawpalApplication.class, args);
//     }

// }
// package com.tidal.pawpal;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// // --- AGGIUNGI QUESTI DUE IMPORT ---
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @SpringBootApplication
// // --- AGGIUNGI QUESTE DUE ANNOTAZIONI ---
// @ComponentScan(basePackages = "com.tidal.pawpal")
// @EnableJpaRepositories(basePackages = "com.tidal.pawpal.repositories")
// public class PawpalApplication {

// 	public static void main(String[] args) {
// 		SpringApplication.run(PawpalApplication.class, args);
// 	}

// }
// package com.tidal.pawpal;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// // --- AGGIUNGI QUESTI TRE IMPORT ---
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @SpringBootApplication
// // --- AGGIUNGI QUESTE TRE ANNOTAZIONI ---
// @ComponentScan(basePackages = "com.tidal.pawpal")
// @EnableJpaRepositories(basePackages = "com.tidal.pawpal.repositories")
// @EntityScan(basePackages = "com.tidal.pawpal.models") // <-- QUESTA È LA RIGA CHIAVE MANCANTE
// public class PawpalApplication {

// 	public static void main(String[] args) {
// 		SpringApplication.run(PawpalApplication.class, args);
// 	}

// }

// package com.tidal.pawpal;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// // --- AGGIUNGI QUESTI TRE IMPORT ---
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @SpringBootApplication
// // --- AGGIUNGI QUESTE TRE ANNOTAZIONI ---
// @ComponentScan(basePackages = "com.tidal.pawpal")
// @EnableJpaRepositories(basePackages = "com.tidal.pawpal.repositories")
// @EntityScan(basePackages = "com.tidal.pawpal.models") // <-- QUESTA È LA RIGA CHIAVE PER L'ERRORE
// public class PawpalApplication {

// 	public static void main(String[] args) {
// 		SpringApplication.run(PawpalApplication.class, args);
// 	}

// }
