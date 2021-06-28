package com.spaf.surveys.security.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spaf.surveys.surveys.model.Response;
import com.spaf.surveys.surveys.model.Survey;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public @Data
class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String ipAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    private List<Survey> surveys;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    private List<Response> responses;

    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    private Boolean enabled = false;
    private Boolean locked = false;

    public AppUser(
            String firstName,
            String lastName,
            String email,
            String password,
            AppUserRole role,
            String ipAddress
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ipAddress = ipAddress;
    }

    public AppUser(UUID userId) {
        this.id = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //    TODO: expiration check
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
