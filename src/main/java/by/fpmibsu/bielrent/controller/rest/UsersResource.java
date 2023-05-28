package by.fpmibsu.bielrent.controller.rest;

import by.fpmibsu.bielrent.model.service.UserService;
import by.fpmibsu.bielrent.utility.JsonMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.stream.Collectors;


@Path("/users")
public class UsersResource {
    UserService userService = UserService.getInstance();
    JsonMapper jsonMapper = JsonMapper.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        var users = userService.getAllUsers().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        var jsonUsers = jsonMapper.toJson(users);
        return Response.ok().entity(jsonUsers).build();
    }
}