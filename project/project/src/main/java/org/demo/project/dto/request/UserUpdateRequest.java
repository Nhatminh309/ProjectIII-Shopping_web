package org.demo.project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserUpdateRequest {
    private String pass_word;
    private String email;

}
