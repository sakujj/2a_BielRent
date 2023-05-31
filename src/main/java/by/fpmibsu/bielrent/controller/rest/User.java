package by.fpmibsu.bielrent.controller.rest;

import by.fpmibsu.bielrent.controller.ErrorHandler;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.UserReq;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.service.UserService;
import by.fpmibsu.bielrent.utility.JsonMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/user")
public class User {
    UserService userService = UserService.getInstance();
    JsonMapper jsonMapper = JsonMapper.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(
            @QueryParam("id") int id
    ) {
        Optional<by.fpmibsu.bielrent.model.entity.User> user;
        try {
            user = userService.getUser(id);
        } catch (DaoException e) {
            return Response.status(ErrorHandler.INTERNAL_ERROR).build();
        }

        if (user.isPresent()) {
            var jsonUser = jsonMapper.toJson(user.get());
            return Response.ok().entity(jsonUser).build();
        } else {
            return Response.status(ErrorHandler.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertUser(
            @QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("password") String password
    ) {
        var user = UserReq.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        try {
            userService.insertIfValid(user);
            return Response.ok().entity("Successful request").build();
        } catch (DaoException e) {
            return Response.status(ErrorHandler.INTERNAL_ERROR).build();
        } catch (ValidationException e) {
            return Response.status(ErrorHandler.BAD_REQUEST).entity(e.getErrors()).build();
        }
    }
}