package com.stacksimplify.restservices.dtos;

public class UserMsDto {

    private Long userid;
    private String username;
    private String emailadress;
    private String rolename;

    public UserMsDto() {
    }

    public UserMsDto(Long userid, String username, String emailadress, String rolename) {
        this.userid = userid;
        this.username = username;
        this.emailadress = emailadress;
        this.rolename = rolename;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailadress() {
        return emailadress;
    }

    public void setEmailadress(String emailadress) {
        this.emailadress = emailadress;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
