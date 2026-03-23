package jp.co.monocrea.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

    @NotBlank(message = "ユーザーIDは必須です")
    @Size(max = 50, message = "ユーザーIDは50文字以内で入力してください")
    public String userId;

    @NotBlank(message = "ユーザー名は必須です")
    @Size(max = 100, message = "ユーザー名は100文字以内で入力してください")
    public String userName;
}