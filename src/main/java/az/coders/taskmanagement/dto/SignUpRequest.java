package az.coders.taskmanagement.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
     String firstName;
     String lastName;
     String email;
     String password;
}
