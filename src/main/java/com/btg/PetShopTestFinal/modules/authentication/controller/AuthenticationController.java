package com.btg.PetShopTestFinal.modules.authentication.controller;

import com.btg.PetShopTestFinal.infra.security.TokenService;
import com.btg.PetShopTestFinal.modules.authentication.dto.LoginRequest;
import com.btg.PetShopTestFinal.modules.authentication.dto.TokenResponse;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Operation(summary = "Login authentication", description = "Returns a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest){

        var autheticate = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        var authentication = authenticationManager.authenticate(autheticate);
        var token = tokenService.tokenGenerate((Customer) authentication.getPrincipal());

        return ResponseEntity.ok().body(new TokenResponse(token));
    }
}
