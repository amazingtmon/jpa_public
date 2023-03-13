package jpa.jpazone.domain.enumpackage;

public enum RoleGroup {

    ADMIN("관리자"),
    USER("일반유저");

    private String role_title;

    RoleGroup(String role_title) {
        this.role_title = role_title;
    }

    public String getTitle(){
        return role_title;
    }
}
