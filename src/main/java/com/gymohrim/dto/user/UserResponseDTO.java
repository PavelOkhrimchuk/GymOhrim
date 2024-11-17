package com.gymohrim.dto.user;

import com.gymohrim.entity.account.AuthProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Integer id;
    private String email;
    private String name;
    private AuthProviderType authProviderType;
}
