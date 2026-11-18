package az.coders.task_management.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterResponseDto {

    private Long id;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;
}

