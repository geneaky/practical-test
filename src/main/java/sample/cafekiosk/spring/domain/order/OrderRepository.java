package sample.cafekiosk.spring.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.expression.spel.ast.OpOr;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.registeredDAteTime >= :startDateTime and o.registeredDAteTime < :endDateTime and o.orderStatus = :orderStatus")
    List<Order> findOrdersBy(LocalDateTime startDateTime, LocalDateTime endDateTime, OrderStatus orderStatus);
}
