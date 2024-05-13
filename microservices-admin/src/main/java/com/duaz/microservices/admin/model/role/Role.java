package com.duaz.microservices.admin.model.role;

public enum Role {
    ROLE_USER("Normal User"),
    ROLE_SA("Super Administrator"),
    LJK_INPUTER("LJK Inputer"),
    LJK_APPROVER("LJK Approver");

    private final String label;

    private Role(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

}
