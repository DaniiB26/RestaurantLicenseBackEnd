package com.example.restaurants.controller.requestClasses;

import java.util.List;

public class FilterRequest {
    private String oras;
    private List<String> tipuri;
    private Double ratingMin;
    private Integer pretMin;

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public List<String> getTipuri() {
        return tipuri;
    }

    public void setTipuri(List<String> tipuri) {
        this.tipuri = tipuri;
    }

    public Double getRatingMin() {
        return ratingMin;
    }

    public void setRatingMin(Double ratingMin) {
        this.ratingMin = ratingMin;
    }

    public Integer getPretMin() {
        return pretMin;
    }

    public void setPretMin(Integer pretMin) {
        this.pretMin = pretMin;
    }
}
