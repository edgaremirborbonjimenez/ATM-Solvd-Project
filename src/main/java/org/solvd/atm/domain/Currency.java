package org.solvd.atm.domain;

public class Currency {
    private Integer id;
    private String name;
    private Double equivalentToDollar;

    public Currency(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getEquivalentToDollar() {
        return equivalentToDollar;
    }

    public void setEquivalentToDollar(Double equivalentToDollar) {
        this.equivalentToDollar = equivalentToDollar;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", equivalentToDollar=" + equivalentToDollar +
                '}';
    }
}
