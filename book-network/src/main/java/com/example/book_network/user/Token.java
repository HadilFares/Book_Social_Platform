package com.example.book_network.user;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder   //* makes it easier to create instances of the classe in readable way
@AllArgsConstructor
@NoArgsConstructor
@Entity   //* map user java  object to the db
public class Token {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;
    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;
}
