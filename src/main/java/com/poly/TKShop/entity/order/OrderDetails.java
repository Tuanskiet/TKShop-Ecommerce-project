package com.poly.TKShop.entity.order;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.poly.TKShop.entity.product.Product;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="order_item")
public class OrderItem  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    private Integer quantity;

    @Column(name="price", precision = 12, scale = 3)
    private BigDecimal price;

    @JsonGetter("price")
    public BigDecimal getPrice(){
        return  this.getProduct().getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

}
