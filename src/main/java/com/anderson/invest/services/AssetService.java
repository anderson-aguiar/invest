package com.anderson.invest.services;

import com.anderson.invest.dtos.AssetRequestDTO;
import com.anderson.invest.dtos.AssetResponseDTO;
import com.anderson.invest.entities.Asset;
import com.anderson.invest.mappers.AssetMapper;
import com.anderson.invest.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetMapper assetMapper;

    @Transactional
    public AssetResponseDTO insert(AssetRequestDTO requestDTO) {
        Asset asset = assetMapper.toEntity(requestDTO);
        Asset saveAsset = assetRepository.save(asset);

        return assetMapper.toResponseDTO(saveAsset);
    }

    @Transactional
    public void delete(Long id){
        Optional<Asset> asset = assetRepository.findById(id);
        if(asset.isPresent()){
            assetRepository.delete(asset.get());
        }
    }
}
