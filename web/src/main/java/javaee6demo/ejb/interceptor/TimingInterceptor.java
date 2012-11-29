package javaee6demo.ejb.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * @author Matija Mazi <br/>
 * @created 11/29/12 7:28 PM
 */
public class TimingInterceptor implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(TimingInterceptor.class);

    @AroundInvoke
    public Object timeMethod(InvocationContext ctx) throws Exception {
        long start = System.currentTimeMillis();
        Object result = ctx.proceed();
        log.info("Trajalo je {} sekund: {}", (System.currentTimeMillis() - start) / 1000., ctx.getMethod());
        return result;
    }
}
