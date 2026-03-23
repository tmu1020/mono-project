package jp.co.monocrea.dto;

public class UserDeleteResponseDTO {

    public Long id;
    public String message;

    public UserDeleteResponseDTO(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}