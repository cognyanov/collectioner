package collectioner.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDemoteRoleBindingModel {
    @NotBlank()
    @Size(min = 3, max = 20)
    public String usernameDemote;
    public String roleDemote;

    public String getUsernameDemote() {
        return usernameDemote;
    }

    public void setUsernameDemote(String usernameDemote) {
        this.usernameDemote = usernameDemote;
    }

    public String getRoleDemote() {
        return roleDemote;
    }

    public void setRoleDemote(String roleDemote) {
        this.roleDemote = roleDemote;
    }
}
