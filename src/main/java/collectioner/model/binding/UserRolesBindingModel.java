package collectioner.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRolesBindingModel {
    @NotBlank()
    @Size(min = 3, max = 20)
    public String username;
    public String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
