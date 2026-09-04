package com.ecommerce.webapi.model;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"") // បន្ថែមចំណុចនេះដើម្បីគ្របដណ្តប់ពាក្យ user
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @OneToOne(mappedBy = "user")
    private StoreName store;
}