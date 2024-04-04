package adapter;

import Blog.User;

public class UserAdapter {
    private User user;

    public UserAdapter(User user) {
        this.user = user;
    }

    public String getFullName() {
        return user.getUsername() + " (" + user.getRealName() + ")";
    }

    public Integer getUserId() {
        return user.getUserId();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getRealName() {
        return user.getRealName();
    }

    public String getDateOfBirth() {
        return user.getDateOfBirth();
    }

    public String getGender() {
        return user.getGender();
    }

    public String getCountry() {
        return user.getCountry();
    }

    public String getDescription() {
        return user.getDescription();
    }

    public Boolean getAdmin() {
        return user.getAdmin();
    }
}
