package javaee6demo.ejb.tx;

import javaee6demo.model.Order;
import javaee6demo.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Matija Mazi <br/>
 * @created 12/1/12 10:07 AM
 *
 * Container-managed transactions demo.
 */
//@Startup @Singleton @DependsOn("PersistentDataInitializer")
public class TransactionsExample {
    private static final Logger log = LoggerFactory.getLogger(TransactionsExample.class);

    @PersistenceContext private EntityManager em;
    @EJB private OrderCalculator priceCalculator;

//    @PostConstruct
    public void postConstruct() {
        createAnOrder();
        calculateAllTotals();
    }

    public void createAnOrder() {
        Order secondOrder = new Order(null);
        Product lestev = em.createQuery("select p from Product p where p.name = :productName", Product.class)
                .setParameter("productName", "Lestev")
                .getSingleResult();
        secondOrder.addItem(lestev, null);
        em.persist(secondOrder);
        em.flush();
    }

    public void calculateAllTotals() {
        List<Order> orders = em.createQuery("select o from Order o order by o.created", Order.class).getResultList();

        for (Order order : orders) {
            try {
                priceCalculator.calculateAndSaveTotal(order);
            } catch (Exception e) {
                log.error("Error calculating and saving total for order {}: {}", order, e.toString());
            }
        }
    }
}
