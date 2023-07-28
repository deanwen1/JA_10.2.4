package com.example.creationbookservice;

import com.example.creationbookservice.model.Book;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;
import java.util.function.Supplier;

@SpringBootApplication
@EnableScheduling
public class CreationBookServiceApplication {

    public static Long increment = 0L;

    public static void main(String[] args) {
        SpringApplication.run(CreationBookServiceApplication.class, args);
    }

    @Bean
    @Scheduled(fixedRate = 10000)
    public Supplier<Book> bookProcessing() {
//        return () -> {
//            increment++;
//            System.out.println(increment);
//            return new Book.Builder()
//                    .setId(increment)
//                    .setName("Book" + increment)
//                    .setDescription("Description" + increment)
//                    .setStatus("unchecked")
//                    .setPrice(10.0 )
//                    .build();
//        };
        return () -> {
            Random random = new Random();
            Long id = Long.valueOf(random.nextInt(1000));
            String name = "Book" + id;
            String description = "Description" + id;
            String status = "unchecked";
            double price = Math.round(random.nextDouble() * 1000);

            return new Book.Builder()
                    .setId(id)
                    .setName(name)
                    .setDescription(description)
                    .setStatus(status)
                    .setPrice(price)
                    .build();
        };
    }

    @Bean
    public NewTopic bookTopic(KafkaAdmin kafkaAdmin) {
        return new NewTopic("book-topic", 3, (short) 1);
    }
}
