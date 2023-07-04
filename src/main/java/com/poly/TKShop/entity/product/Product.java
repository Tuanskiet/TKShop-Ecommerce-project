package com.poly.TKShop.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Integer productId;
    private String name;
    private Double price;
    private String[] colors;
    private String[] sizes;
    private String material;
    private int quantityLeft;
    private int quantitySold;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateRelease;
    private String slug;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_type_id")
    private ProductType type;
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductImage> images;

}
