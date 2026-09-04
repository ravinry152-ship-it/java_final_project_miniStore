package com.ecommerce.webapi.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class TelegramBotConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "store_id",
            unique = true,
            nullable = false
    )
    private StoreName store;

    @Column(nullable = false, unique = true)
    private String chatId;

    private boolean active;

    private LocalDateTime createdAt;
}