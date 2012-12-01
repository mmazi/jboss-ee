package javaee6demo.ejb.tx;

import javaee6demo.model.Order;
import javaee6demo.model.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;

/**
 * @author Matija Mazi <br/>
 * @created 12/1/12 10:03 AM
 */
@Stateless
public class OrderCalculator {
    private static final Logger log = LoggerFactory.getLogger(OrderCalculator.class);

//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void calculateAndSaveTotal(Order order) {
        double total = 0.;
        for (OrderItem item : order.getItems()) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        if (total > 200) {
            total *= .95;
        }
        log.debug("Setting total to {} on order {}", total, order);
        order.setTotal(total);
    }
}
