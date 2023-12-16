package com.btg.PetShopTestFinal.modules.costumers.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerResponse {
    private String idTransaction;
    private String name;
    private String email;
}
