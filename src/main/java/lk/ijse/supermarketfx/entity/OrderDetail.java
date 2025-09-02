package lk.ijse.supermarketfx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "item_code")
    private Item item;
    private int quantity;
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal price;
}
