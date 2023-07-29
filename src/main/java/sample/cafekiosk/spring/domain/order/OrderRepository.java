package sample.cafekiosk.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpOr;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
