package com.sartop.demoproductsapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String Username;
    @Column(name = "password", nullable = false)
    private String Password;
    @Column(name = "name", nullable = false)
    private String Name;
    @Enumerated(EnumType.STRING)
    @Column(name = "userRole", nullable = false, length = 25)
    private Role UserRole = Role.ROLE_ADMIN;

    @Column(name = "creationDate")
    private LocalDateTime CreationDate;
    @Column(name = "modificationDate")
    private LocalDateTime ModificationDate;
    @Column(name = "createBy")
    private String CreateBy;

    @Column(name = "modifiedBy")
    private String ModifiedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "Id=" + Id +
                '}';
    }

    public enum Role
    {
        ROLE_ADMIN,  ROLE_CLIENT
    }
}
