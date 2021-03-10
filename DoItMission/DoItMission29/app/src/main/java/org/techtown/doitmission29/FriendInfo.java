package org.techtown.doitmission29;

public class FriendInfo {
    String regId;
    String name;
    String phone;

    public FriendInfo(String regId, String name, String phone) {
        this.regId = regId;
        this.name = name;
        this.phone = phone;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
