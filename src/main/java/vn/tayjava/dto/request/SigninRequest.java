package vn.tayjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import vn.tayjava.util.Platform;

import java.io.Serializable;

@Getter
@Builder
public class SigninRequest implements Serializable {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Platform is required")
    private Platform platform;

    @NotBlank(message = "Device token is required")
    private String deviceToken;

    private String version;

}
