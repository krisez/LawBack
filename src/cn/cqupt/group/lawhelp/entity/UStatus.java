package cn.cqupt.group.lawhelp.entity;

public class UStatus {
    /**data 1 ----  成功登陆
     *      2  ----  失败数据
     *      3  ----  注册成功
     */
    private String status;
    private String message;
    private UserInfo data;

    public UStatus() {
    }

    public UStatus(String status, String message, UserInfo data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
