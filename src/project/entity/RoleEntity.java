package project.entity;

import java.util.Objects;

public class RoleEntity {
    private Integer id;
    private String role;

    public RoleEntity(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public RoleEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
               "id=" + id +
               ", role='" + role + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RoleEntity that = (RoleEntity) object;
        return Objects.equals(id, that.id) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
