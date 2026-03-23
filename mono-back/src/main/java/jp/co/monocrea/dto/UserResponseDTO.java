package jp.co.monocrea.dto;

public class UserResponseDTO {

    public Long id;
    public String userId;
    public String userName;

    public UserResponseDTO(Long id, String userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }
}