package Blog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonProperty("user_id")
    private Integer userId;
    private String username;
    private String password;
    @JsonProperty("realname")
    private String realName;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    private String gender;
    private String country;
    private String description;

    @JsonProperty("is_admin")
    private Boolean admin;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void String(String dateOfBirth) {
        this.dateOfBirth = String.valueOf(dateOfBirth);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    public boolean isAdmin() {
        return admin != null && admin;
    }


}
