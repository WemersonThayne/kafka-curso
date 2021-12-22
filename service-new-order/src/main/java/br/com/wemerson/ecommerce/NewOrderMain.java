package br.com.wemerson.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    private static final String TOPIC_NAME_ORDER = "ECOMMERCE_NEW_ORDER";
    private static final String TOPIC_NAME_SEND_EMAIL = "ECOMMERCE_SEND_EMAIL";

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        try (var dispatcher = new KafkaDispatcher()) {
            for (var i = 0; i < 10; i++) {

                var userId = UUID.randomUUID().toString();
                var orderId = UUID.randomUUID().toString();
                var amount = new BigDecimal(Math.random() * 5000 + 1);
                var order = new Order(userId,orderId,amount);

                dispatcher.send(TOPIC_NAME_ORDER, userId, order);
                var email = "Thank you for your order! We are processing your order!";
                dispatcher.send(TOPIC_NAME_SEND_EMAIL, userId, email);
            }
        }
	}

}
