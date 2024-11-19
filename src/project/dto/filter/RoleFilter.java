package project.dto.filter;

import java.util.Objects;

public class RoleFilter {
    private int limit;
    private int offset;
    private String role;

    public RoleFilter(int limit, int offset, String role) {
        this.limit = limit;
        this.offset = offset;
        this.role = role;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RoleFilter that = (RoleFilter) object;
        return limit == that.limit && offset == that.offset && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, offset, role);
    }

    @Override
    public String toString() {
        return "RoleFilter{" +
               "limit=" + limit +
               ", offset=" + offset +
               ", role='" + role + '\'' +
               '}';
    }
}
