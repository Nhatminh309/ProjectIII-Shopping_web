package org.demo.project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {
    private String user_name;
    private String pass_word;
    private String email;

}
