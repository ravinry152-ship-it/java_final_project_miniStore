package com.ecommerce.webapi.repository;

import com.ecommerce.webapi.model.TelegramBotConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeleGramBotRepository extends JpaRepository< TelegramBotConnection ,Long> {
}
