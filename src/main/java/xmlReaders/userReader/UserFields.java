package xmlReaders.userReader;


public enum UserFields {
    USERS("users"),
    USER("user"),
    NAME("name"),
    SURNAME("surname"),
    LOGIN("login");

    private String tagName;

    UserFields(String tag) {
        tagName = tag;
    }

    public String getTagName() {
        return tagName;
    }
}
