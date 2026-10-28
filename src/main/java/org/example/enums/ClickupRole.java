package org.example.enums;

public enum ClickupRole {
    OWNER("owner"),
    ADMIN("admin"),
    MEMBER("member"),
    GUEST("guest");

    private final String key;
    ClickupRole(String key){
        this.key = key;
    }
    public String getKey(){
        return key;
    }
    public static ClickupRole fromKey(String roleKey) {
        if (roleKey == null) return null;
        for (ClickupRole role : ClickupRole.values()) {
            if (role.key.equalsIgnoreCase(roleKey)) {
                return role;
            }
        }
        return null;
    }

}
