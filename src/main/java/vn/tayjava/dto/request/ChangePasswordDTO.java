package vn.tayjava.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ChangePasswordDTO implements Serializable {
    private String secretKey;
    private String newPassword;
    private String confirmPassword;
}
