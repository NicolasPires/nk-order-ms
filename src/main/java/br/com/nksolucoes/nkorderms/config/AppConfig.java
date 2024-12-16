package br.com.nksolucoes.nkorderms.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppConfig {
	private String orderCalculatedChannel = "orderCalculatedProducer-out-0";
}
