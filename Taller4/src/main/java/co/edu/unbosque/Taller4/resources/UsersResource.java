package co.edu.unbosque.Taller4.resources;
import co.edu.unbosque.Taller4.dtos.ExceptionMessage;
import co.edu.unbosque.Taller4.dtos.NFT;
import co.edu.unbosque.Taller4.dtos.User;
import co.edu.unbosque.Taller4.services.NFTService;
import co.edu.unbosque.Taller4.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context ;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Path("/users")
public class UsersResource {
    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<User> users = new UserService().getUsers();

            return Response.ok()
                    .entity(users)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        String contextPath =context.getRealPath("") + File.separator;

        try {
            user = new UserService().createUser(user.getUsername(), user.getPassword(), user.getRole(), user.getMoney(), contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(user.getUsername()).build())
                    .entity(user)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username) {
        try {
            List<User> users = new UserService().getUsers();

            User user = users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                return Response.ok()
                        .entity(user)
                        .build();
            } else {
                return Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("role") String role,
            @FormParam("money") String money
    ) {
        String contextPath =context.getRealPath("") + File.separator;

        try {
            User user = new UserService().createUser(username, password, role, money, contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                    .entity(user)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/arts")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createPiece(
            @FormParam("username") String username,
            @FormParam("role") String role,
            @FormParam("titulo") String titulo,
            @FormParam("money") String money

    ){
        String contextPath = context.getRealPath("") + File.separator;

        try {
            NFT nft = new NFTService().createNFT(username, role, titulo, money, contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                    .entity(nft)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/recharge")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response rechargeFCoins(
            @FormParam("username") String username,
            @FormParam("money") String money
    ){
        String contextPath = context.getRealPath("") + File.separator;

        try {
            List<User> users = new UserService().getUsers();
            User user = null;

            for (User User : users){
                if (User.getUsername().equalsIgnoreCase(username)){
                    user = User;
                    users.remove(user);
                    break;
                }
            }

            String currentMoney = user.getMoney();
            String newMoney = String.valueOf(Integer.parseInt(currentMoney) + Integer.parseInt(money));
            user.setMoney(newMoney);
            new UserService().createUser(user.getUsername(), user.getPassword(), user.getRole(), user.getMoney(), contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                    .entity(user)
                    .build();

        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
}
