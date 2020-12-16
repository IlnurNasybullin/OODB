package lab_8.dbManager;

import java.util.Objects;

public class DatabaseProperties {

    private String url;
    private String user;
    private String password;

    public DatabaseProperties() {}

    public DatabaseProperties(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseProperties that = (DatabaseProperties) o;
        return Objects.equals(url, that.url) && Objects.equals(user, that.user) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, user, password);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DatabaseProperties{");
        sb.append("url='").append(url).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
