package jp.co.monocrea.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.panache.common.Sort;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import jp.co.monocrea.dto.PageResponseDTO;
import jp.co.monocrea.dto.UserRequestDTO;
import jp.co.monocrea.dto.UserResponseDTO;
import jp.co.monocrea.entity.UserEntity;
import jp.co.monocrea.exception.ConflictException;
import jp.co.monocrea.exception.NotFoundException;
import jp.co.monocrea.repository.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    // -----------------------------
    // 一覧取得
    // -----------------------------
    public PageResponseDTO<UserResponseDTO> findAll(
            int page,
            int size,
            String sort,
            String userId,
            String userName
    ) {

        StringBuilder query = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (userId != null && !userId.isBlank()) {
            query.append(" and userId like :userId");
            params.put("userId", "%" + userId + "%");
        }

        if (userName != null && !userName.isBlank()) {
            query.append(" and userName like :userName");
            params.put("userName", "%" + userName + "%");
        }

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        String sortOrder = sortParts.length > 1 ? sortParts[1] : "asc";

        if (!List.of("id", "userId", "userName").contains(sortField)) {
            sortField = "id";
        }

        Sort.Direction direction =
                sortOrder.equalsIgnoreCase("desc")
                        ? Sort.Direction.Descending
                        : Sort.Direction.Ascending;

        Sort panacheSort = Sort.by(sortField, direction);

        PanacheQuery<UserEntity> panacheQuery =
                userRepository.search(query.toString(), panacheSort, params);

        long total = panacheQuery.count();

        List<UserResponseDTO> users =
                panacheQuery.page(Page.of(page, size))
                        .list()
                        .stream()
                        .map(entity -> new UserResponseDTO(
                                entity.id,
                                entity.userId,
                                entity.userName
                        ))
                        .toList();

        return new PageResponseDTO<>(
                total,
                page,
                size,
                users
        );
    }

    // -----------------------------
    // 単体取得
    // -----------------------------
    public UserResponseDTO findById(Long id) {

        UserEntity entity = userRepository.findById(id);

        if (entity == null) {
            throw new NotFoundException("ユーザーが見つかりませんでした");
        }

        return new UserResponseDTO(
                entity.id,
                entity.userId,
                entity.userName
        );
    }

    // -----------------------------
    // 新規作成
    // -----------------------------
    @Transactional
    public UserResponseDTO create(UserRequestDTO req) {

        if (userRepository.findByUserId(req.userId) != null) {
            throw new ConflictException("既に存在するユーザーIDです");
        }

        UserEntity entity = new UserEntity();
        entity.userId = req.userId;
        entity.userName = req.userName;

        userRepository.persist(entity);

        return new UserResponseDTO(
                entity.id,
                entity.userId,
                entity.userName
        );
    }

    // -----------------------------
    // 更新
    // -----------------------------
    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO req) {

        UserEntity entity = userRepository.findById(id);

        if (entity == null) {
            throw new NotFoundException("ユーザーが見つかりませんでした");
        }

        UserEntity duplicate = userRepository.findByUserId(req.userId);
        if (duplicate != null && !duplicate.id.equals(id)) {
            throw new ConflictException("既に存在するユーザーIDです");
        }

        entity.userId = req.userId;
        entity.userName = req.userName;

        return new UserResponseDTO(
                entity.id,
                entity.userId,
                entity.userName
        );
    }

    // -----------------------------
    // 削除
    // -----------------------------
    @Transactional
    public void delete(Long id) {

        UserEntity entity = userRepository.findById(id);

        if (entity == null) {
            throw new NotFoundException("ユーザーが見つかりませんでした");
        }

        userRepository.delete(entity);
    }
}