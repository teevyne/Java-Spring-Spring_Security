package ro.cpatrut.auth.dto.types;

public enum GrantType {
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token");

    private final String type;

    GrantType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
