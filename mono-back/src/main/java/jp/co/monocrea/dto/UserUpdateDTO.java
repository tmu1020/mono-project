package jp.co.monocrea.dto;

import jakarta.validation.constraints.NotBlank;

public class UserUpdateDTO {

    @NotBlank
    public String userName;

}