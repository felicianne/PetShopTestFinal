package com.btg.PetShopTestFinal.modules.costumers.usecase;

import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCustomer {
    private final CustomerRepository repository;

    public List<CustomerResponse> execute() {
        return CustomerConvert.toListResponse(repository.findAll());
    }
}
