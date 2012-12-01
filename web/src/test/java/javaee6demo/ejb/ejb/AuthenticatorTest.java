package javaee6demo.ejb.ejb;

import javaee6demo.PersistentDataInitializer;
import javaee6demo.ejb.cdi.Authenticator;
import javaee6demo.ejb.cdi.Credentials;
import javaee6demo.ejb.cdi.LoginEvent;
import javaee6demo.ejb.cdi.UserCreator;
import javaee6demo.model.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * @author Matija Mazi <br/>
 * @created 12/1/12 7:05 PM
 */
public class AuthenticatorTest extends Arquillian {
    private static final Logger log = LoggerFactory.getLogger(AuthenticatorTest.class);

    @Inject private Authenticator authenticator;
    @Inject private Credentials credentials;

    @Test
    public void testLoginSucceed() throws Exception {
        credentials.setUsername("mm");
        credentials.setPassword("a");
        Assert.assertEquals(authenticator.login(), "logged-in");
    }

    @Test
    public void testLoginFail() throws Exception {
        credentials.setUsername("obama");
        credentials.setPassword("whitehouse");
        Assert.assertNull(authenticator.login());
    }

    @Deployment
    public static Archive createDeployment() {
        Archive archive = ShrinkWrap.create(WebArchive.class)
                .addPackage(User.class.getPackage())
                .addClass(Authenticator.class)
                .addClass(Credentials.class)
                .addClass(PersistentDataInitializer.class)
                .addClass(UserCreator.class)
                .addClass(LoginEvent.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/persistence.xml");
        log.debug("archive = {}", archive.toString(true));
        return archive;
    }
}
