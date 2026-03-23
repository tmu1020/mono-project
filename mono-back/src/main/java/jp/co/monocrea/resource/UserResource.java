package jp.co.monocrea.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.monocrea.dto.PageResponseDTO;
import jp.co.monocrea.dto.UserRequestDTO;
import jp.co.monocrea.dto.UserResponseDTO;
import jp.co.monocrea.service.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService service;

    // 新規作成
    @POST
    public Response create(@Valid UserRequestDTO req) {
        UserResponseDTO res = service.create(req);
        return Response.status(Response.Status.CREATED).entity(res).build();
    }

    // 一覧取得(ページネーション)
    @GET
    public PageResponseDTO<UserResponseDTO> findAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("5") int size,
            @QueryParam("sort") @DefaultValue("id,asc") String sort,
            @QueryParam("userId") String userId,
            @QueryParam("userName") String userName
    ) {

        return service.findAll(page, size, sort, userId, userName);
    }

    // 単体取得
    @GET
    @Path("/{id}")
    public UserResponseDTO find(@PathParam("id") Long id) {
        return service.findById(id);
    }

    // 更新
    @PUT
    @Path("/{id}")
    public UserResponseDTO update(
            @PathParam("id") Long id,
            @Valid UserRequestDTO req) {
        return service.update(id, req);
    }

    // 削除
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}