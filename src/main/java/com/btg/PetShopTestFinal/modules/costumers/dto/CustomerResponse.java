package com.btg.PetShopTestFinal.modules.costumers.dto;

import lombok.*;

@Data
public class CustomerResponse {
    private String idTransaction;
    private String name;
    private String email;
    private String address;
}
