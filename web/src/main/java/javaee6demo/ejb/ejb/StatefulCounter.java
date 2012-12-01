package javaee6demo.ejb.ejb;

import javax.ejb.Stateful;

/**
 * @author Matija Mazi <br/>
 * @created 8.2.12 18:07
 */
@Stateful
public class StatefulCounter {
    private int count = 0;

    public void next() {
        ++count;
    }

    public int getCount() {
        return count;
    }
}
