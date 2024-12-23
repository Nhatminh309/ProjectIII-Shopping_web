package org.demo.project.dto.request;

public class ManagerCreationRequest {
    private String user_name;
    private String pass_word;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public ManagerCreationRequest(String user_name, String pass_word) {
        this.user_name = user_name;
        this.pass_word = pass_word;
    }
}
