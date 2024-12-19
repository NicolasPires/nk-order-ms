package br.com.nksolucoes.nkorderms.utils;

public class TestJsonUtils {

	public static final String VALID_ORDER_REQUEST_JSON = """
            {
              "customer": {
                "document": "12345678900",
                "name": "John Doe",
                "email": "johndoe@example.com",
                "phone": "123456789"
              },
              "items": [
                {
                  "description": "Item 1",
                  "quantity": 2,
                  "price": 10.0
                },
                {
                  "description": "Item 2",
                  "quantity": 1,
                  "price": 20.0
                }
              ]
            }
            """;

	public static final String INVALID_ORDER_REQUEST_JSON = """
            {
              "customer": {
                "document": "",
                "name": "",
                "email": "invalidemail.com",
                "phone": ""
              },
              "items": []
            }
            """;

	public static String orderRequestWithCustomCustomer(String document, String name, String email, String phone) {
		return """
                {
                  "customer": {
                    "document": "%s",
                    "name": "%s",
                    "email": "%s",
                    "phone": "%s"
                  },
                  "items": [
                    {
                      "description": "Item 1",
                      "quantity": 2,
                      "price": 10.0
                    }
                  ]
                }
                """.formatted(document, name, email, phone);
	}
}
