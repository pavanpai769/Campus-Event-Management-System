package org.eventmanagement.datamanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NonNull
    @Column(unique=true)
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String name;

    @NonNull
    @Column(unique=true)
    private String email;

    private boolean isApproved;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    @Column(name="is_deleted")
    private boolean isDeleted = false;
}
