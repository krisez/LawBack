package cn.cqupt.group.lawhelp.entity;

import java.io.Serializable;

public class User{
    private String user;
    private String role;
    private String logindevice;

    public User() {
    }

    public User(String user,String role, String logindevice) {
        this.user = user;
        this.role = role;
        this.logindevice = logindevice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogindevice() {
        return logindevice;
    }

    public void setLogindevice(String logindevice) {
        this.logindevice = logindevice;
    }
}
