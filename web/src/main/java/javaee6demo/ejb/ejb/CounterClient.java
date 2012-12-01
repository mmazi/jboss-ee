package javaee6demo.ejb.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * @author Matija Mazi <br/>
 * @created 8.2.12 18:07
 */
@Startup @Singleton
public class CounterClient {
    private static final Logger log = LoggerFactory.getLogger(CounterClient.class);

    @EJB private Counter counter;

    @PostConstruct
    public void count() {
        log.debug("Counting.... ");
        for (int i = 1; i <= 10000; i++) {
            counter.next();
        }
        log.debug("Final counter value: {}", counter.getCount());
    }

    public int getCount() {
        return counter.getCount();
    }
}
