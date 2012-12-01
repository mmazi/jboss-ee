package javaee6demo.ejb.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ejb.EJB;

/**
 * @author Matija Mazi <br/>
 * @created 12/1/12 7:05 PM
 */
public class CounterClientTest extends Arquillian {
    private static final Logger log = LoggerFactory.getLogger(CounterClientTest.class);

    @EJB private CounterClient counterClient;

    @Test
    public void testCount() throws Exception {
        Assert.assertEquals(counterClient.getCount(), 10000);
    }

    @Deployment
    public static Archive createDeployment() {
        Archive archive = ShrinkWrap.create(JavaArchive.class)
                .addClass(CounterClient.class)
                .addClass(Counter.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        log.debug("archive = {}", archive);
        return archive;
    }
}
