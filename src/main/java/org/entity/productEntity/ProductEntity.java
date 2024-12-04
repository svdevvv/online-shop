package org.entity.productEntity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ProductEntity {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
}
