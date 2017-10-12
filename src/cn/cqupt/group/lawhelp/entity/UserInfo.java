package cn.cqupt.group.lawhelp.entity;

public class UserInfo {
    private String id;
    private String headImgUrl;
    private String nickname;
    private String sex;

    public UserInfo() {
    }

    public UserInfo(String id, String nickname, String headImgUrl, String sex) {
        this.id = id;
        this.headImgUrl = headImgUrl;
        this.nickname = nickname;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
