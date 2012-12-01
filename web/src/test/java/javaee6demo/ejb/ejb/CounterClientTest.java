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

    @EJB private CounterClient1 counterClient1;

    @Test
    public void testCount() throws Exception {
        Assert.assertEquals(counterClient1.getStatefulCount(), 10000);
        Assert.assertEquals(counterClient1.getStatelessCount(), 10000);
    }

    @Deployment
    public static Archive createDeployment() {
        Archive webArchive = ShrinkWrap.create(JavaArchive.class)
                .addClass(CounterClient1.class)
                .addClass(StatefulCounter.class)
                .addClass(StatelessCounterX.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        log.debug("webArchive = {}", webArchive);
        return webArchive;
    }
}
