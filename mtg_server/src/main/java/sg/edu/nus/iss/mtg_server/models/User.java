package sg.edu.nus.iss.mtg_server.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {   

    private String userId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String userName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String userPassword;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;
}
