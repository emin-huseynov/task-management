
package az.coders.task_management.mapper;

import az.coders.task_management.dto.request.RegisterRequestDto;
import az.coders.task_management.dto.response.RegisterResponseDto;
import az.coders.task_management.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(RegisterRequestDto dto) {

        UserEntity user = new UserEntity();

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());

        return user;
    }

    public RegisterResponseDto toResponseDto(UserEntity user) {

        RegisterResponseDto dto = new RegisterResponseDto();

        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }
}
