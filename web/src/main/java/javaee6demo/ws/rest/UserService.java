package javaee6demo.ws.rest;

import javaee6demo.model.Role;
import javaee6demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Matija Mazi <br/>
 * @created 12.2.12 10:36
 */
@Path("users")
@Stateless
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext private EntityManager em;

    @GET
    @Path("list")
    public List<User> getUsers() {
        return new ArrayList<User>(new HashSet<User>(em.createQuery("select u from User u join fetch u.roles", User.class).getResultList()));
    }

    @GET
    @Path("user/{username}")
    @Produces({"application/xml", "application/json"})
    public User getUser(@PathParam("username") String username) {
        return em.find(User.class, username);
    }

    @GET
    @Path("user/{username}/roles")
    public Set<Role> getUserRoles(@PathParam("username") String username) {
        return em.find(User.class, username).getRoles();
    }

    @DELETE
    @Path("user/{username}")
    public void deleteUser(@PathParam("username") String username) {
        em.remove(em.getReference(User.class, username));
    }

    @POST
    @Path("user/{username}")
    public void addUser(@PathParam("username") String username) {
        log.debug("UserService.addUser");
    }

    @POST
    @Path("user")
    @Consumes("*/*")
    public void addUser(User user) {
        log.debug("UserService.addUser {}", user);
        em.persist(user);
    }

    @GET
    @Path("test")
    @Consumes("*/*")
    public String test(User param) {
        log.debug("param = {}", param);
        return "Got: " + param;
    }
}
