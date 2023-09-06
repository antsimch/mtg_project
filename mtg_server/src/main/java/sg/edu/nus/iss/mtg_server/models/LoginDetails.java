package sg.edu.nus.iss.mtg_server.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDetails {

    @NotNull
    @NotEmpty
    @NotBlank
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;
}
