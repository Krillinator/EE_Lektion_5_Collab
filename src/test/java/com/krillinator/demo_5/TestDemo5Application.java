package com.krillinator.demo_5;

import org.springframework.boot.SpringApplication;

// Not necessary to touch this file (most of the time) //
/** It boots your app, but includes TestcontainersConfiguration.
 * Useful for integration or system testing (e.g. @SpringBootTest).
 */
public class TestDemo5Application {

	public static void main(String[] args) {
		SpringApplication.from(Demo5Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}


