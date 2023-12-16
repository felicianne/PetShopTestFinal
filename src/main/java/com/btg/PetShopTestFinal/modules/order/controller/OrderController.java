package com.btg.PetShopTestFinal.modules.order.controller;

import com.btg.PetShopTestFinal.modules.order.dto.OrderRequest;
import com.btg.PetShopTestFinal.modules.order.dto.OrderResponse;
import com.btg.PetShopTestFinal.modules.order.usecase.DeleteOrder;
import com.btg.PetShopTestFinal.modules.order.usecase.FindOrder;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemRequest;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemResponse;
import com.btg.PetShopTestFinal.modules.orderItem.usecase.AddOrderItem;
import com.btg.PetShopTestFinal.modules.orderItem.usecase.ChangeItemAmount;
import com.btg.PetShopTestFinal.modules.orderItem.usecase.DeleteOrderItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    ExecuteOrder createOrder;
    @Autowired
    DeleteOrder deleteOrder;
    @Autowired
    FindOrder findOrder;

    @Autowired
    DeleteOrderItem deleteItemOrder;
    @Autowired
    AddOrderItem addItemOrder;
    @Autowired
    ChangeItemAmount changeAmountItem;

    @Operation(summary = "Create a Order", description = "Returns a Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Not possible create order")
    })
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        OrderResponse orderResponse = createOrder.execute(orderRequest);
        return ResponseEntity
                .created(URI.create("/order/"+orderResponse.getIdTransaction()))
                .body(orderResponse);
    }

    @Operation(summary = "Add items a Order", description = "Returns a Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Not possible add item a order")
    })
    @PostMapping("/{id}")
    public ResponseEntity<OrderItemResponse> addItemOrder(
            @PathVariable String id,
            @RequestBody OrderItemRequest orderItemRequest
    ) throws Exception {
        OrderItemResponse orderItemResponse = addItemOrder.execute(id, orderItemRequest);
        return ResponseEntity
                .created(URI.create("/order/"+id))
                .body(orderItemResponse);
    }

    @Operation(summary = "Get all list Order", description = "Returns a list Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Not possible list a order")
    })
    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrder(){
        return ResponseEntity.ok(findOrder.findAll());
    }

    @Operation(summary = "Get a Order by id", description = "Returns a Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Not possible a order")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> listOrderById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(findOrder.findById(id));
    }

    @Operation(summary = "Update Item a Order", description = "Returns a Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Not possible update order")
    })
    @PutMapping("/ordemItem/{idOrderItem}")
    public ResponseEntity<OrderItemResponse> updateOrder(
            @PathVariable String idOrderItem,
            @RequestBody OrderItemRequest orderItemRequest
    ) throws Exception {
        OrderItemResponse orderItemResponse = changeAmountItem.execute(idOrderItem, orderItemRequest);
        return ResponseEntity.ok().body(orderItemResponse);
    }

    @Operation(summary = "Delete Item a Order", description = "Returns a Order update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Not possible remove item a order")
    })
    @DeleteMapping ("/ordemItem/{idOrderItem}")
    public ResponseEntity<OrderItemResponse> deleteOrderItem(
            @PathVariable String idOrderItem
    ) throws Exception {
        deleteItemOrder.execute(idOrderItem);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Not possible delete order")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> deleteOrder(@PathVariable String id) throws Exception {
        deleteOrder.execute(id);
        return ResponseEntity.noContent().build();
    }
}