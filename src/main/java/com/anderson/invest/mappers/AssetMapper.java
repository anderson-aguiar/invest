package com.anderson.invest.mappers;

import com.anderson.invest.dtos.AssetRequestDTO;
import com.anderson.invest.dtos.AssetResponseDTO;
import com.anderson.invest.entities.Asset;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

    public Asset toEntity(AssetRequestDTO requestDTO){
        if(requestDTO == null) return null;
        Asset asset = new Asset();

        asset.setTicker(requestDTO.ticker());
        asset.setType(requestDTO.type());
        asset.setRiskLevel(requestDTO.riskLevel());

        return asset;
    }

    public AssetResponseDTO toResponseDTO(Asset asset){
        return new AssetResponseDTO(
                asset.getId(),
                asset.getTicker(),
                asset.getType(),
                asset.getRiskLevel());
    }
}
