package com.example.book_network.role;


import com.example.book_network.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder   //* makes it easier to create instances of the classe in readable way
@AllArgsConstructor
@NoArgsConstructor
@Entity   //* map user java  object to the db
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique=true)
    private String name;
    //! non Owning side of the relation: it only references the join table but doesnt reflect changes on it.
    @ManyToMany(mappedBy = "roles")                   //* each Role can be associated with multiple User entities, and each User can have multiple Role entities
    @JsonIgnore
    private List<User> users;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastMofidiedDate;
}
