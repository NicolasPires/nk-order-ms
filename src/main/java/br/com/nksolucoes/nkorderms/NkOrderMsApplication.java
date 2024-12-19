package br.com.nksolucoes.nkorderms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.nksolucoes.nkorderms")
@OpenAPIDefinition(info = @Info(title = "nk-order-ms", version ="1", description = "API developed for knowledge presentation"))
public class NkOrderMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NkOrderMsApplication.class, args);
	}

}
