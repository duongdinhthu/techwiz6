package petcare.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import petcare.domain.enumeration.UserRole;

/**
 * A DTO for the {@link petcare.domain.UserPet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserPetDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 1000)
    private String name;

    @NotNull
    @Size(max = 1000)
    private String email;

    @NotNull
    @Size(max = 1000)
    private String passwordHash;

    @Size(max = 1000)
    private String phone;

    @Size(max = 1000)
    private String address;

    @NotNull
    private UserRole role;

    @Size(max = 1000)
    private String avatar;

    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPetDTO)) {
            return false;
        }

        UserPetDTO userPetDTO = (UserPetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userPetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserPetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", role='" + getRole() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
