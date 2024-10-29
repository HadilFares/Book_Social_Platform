package com.example.book_network.user;

import com.example.book_network.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder   //* makes it easier to create instances of the classe in readable way
@AllArgsConstructor
@NoArgsConstructor
@Entity   //* map user java  object to the db
@Table(name ="user")
@EntityListeners(AuditingEntityListener.class)  //* listen to the lifecycle events of the entity
public class User  implements UserDetails, Principal {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    @Column(unique=true)
    private String email;
    private String password;
    private boolean accountLocket;
    private boolean enabled;
    //! the Owning Side of the relation: any update on USER affects the join table USER_ROLE
    @ManyToMany(fetch = FetchType.EAGER)                //* when a User gets retrieved from the database, the associated Role entities are also retrieved immediately, in the same query.
    private List<Role> roles;


    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastMofidiedDate;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return !accountLocket;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    private String FullName() {
        return firstname + " " + lastname;
    }
}
