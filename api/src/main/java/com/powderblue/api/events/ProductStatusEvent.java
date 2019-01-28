package com.powderblue.api.events;

import com.powderblue.entity.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductStatusEvent implements Serializable {
    private String id;
    private ProductStatus status;

}
