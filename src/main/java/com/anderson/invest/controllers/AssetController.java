package com.anderson.invest.controllers;

import com.anderson.invest.dtos.AssetRequestDTO;
import com.anderson.invest.dtos.AssetResponseDTO;
import com.anderson.invest.services.AssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping
    public ResponseEntity<AssetResponseDTO> insert(@RequestBody @Valid AssetRequestDTO requestDTO) {
        AssetResponseDTO responseDTO = assetService.insert(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assetService.delete(id);
        return ResponseEntity.ok().build();
    }
}
