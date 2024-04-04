package Blog;

import web.API;
import java.io.IOException;
import java.util.List;

public class UserService {
    private API api;

    public UserService() {
        this.api = API.getInstance();
    }

    public User authenticateUser(String username, String password) {
        try {
            // If login successful, return a User instance
            return api.loginUser(username, password);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void logoutUser() {
        try {
            api.logoutUser();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers(User requestor) {
        try {
            return api.getAllUsers(requestor);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteUser(String userId, User requestor) {

        try {
            return api.deleteUser(userId, requestor);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


}
