package com.anderson.invest.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_investment")
@EntityListeners(AuditingEntityListener.class)
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantity;
    private BigDecimal purchasePrice;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate puchaseDate;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    public Investment() {
    }

    public Investment(Long id, Double quantity, BigDecimal purchasePrice, LocalDate puchaseDate, Wallet wallet, Asset asset) {
        this.id = id;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.puchaseDate = puchaseDate;
        this.wallet = wallet;
        this.asset = asset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDate getPuchaseDate() {
        return puchaseDate;
    }

    public void setPuchaseDate(LocalDate puchaseDate) {
        this.puchaseDate = puchaseDate;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Investment that = (Investment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
