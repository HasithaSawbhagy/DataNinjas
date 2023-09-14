package com.dataninjas.inventory_management.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_inventory")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String skuCode;
    private Integer quantity;

}
