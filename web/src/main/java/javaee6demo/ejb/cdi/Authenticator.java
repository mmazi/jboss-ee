package javaee6demo.ejb.cdi;

import javaee6demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author Matija Mazi <br/>
 * @created 9.2.12 12:45
 */
@SessionScoped
@Stateful
@Named
public class Authenticator implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Authenticator.class);

    @Inject private Credentials cred;

    @PersistenceContext private EntityManager em;

    @Inject private Event<LoginEvent> loginEvent;


    private User user;

    private String username;

    public String login() {
        try {
            user = em.createQuery("select u from User u where u.username = :un and u.password = :pw", User.class)
                    .setParameter("un", cred.getUsername())
                    .setParameter("pw", cred.getPassword())
                    .getSingleResult();
        } catch (NoResultException e) {
            log.warn("Error logging in; username: {}", cred.getUsername());
            return null;
        }
        username = user.getUsername();
        loginEvent.fire(new LoginEvent(user));
        log.debug("User {} logged in.", user.getName());
        return "logged-in";
    }

    public String logout() {
        ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().invalidate();
        return "/login.jsf";
    }

    @Produces @Authenticated @Named
    public User getAuthenticatedUser() {
        return user;
    }

    @Produces @Authenticated @Named
    public String getUsername() {
        return username;
    }
}
