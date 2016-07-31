package ua.mishkyroff.carget.entities;

/**
 * Created by U on 26.07.2016.
 */
public class Brand {
    private Integer idBrand;
    private String brandAbbr;
    private String fullName;

    @Override
    public String toString() {
        return " BRAND (idBrand=" + idBrand + " brandShort=" + brandAbbr + " brandFull=" + fullName + ")";
    }

    public Integer getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public String getBrandAbbr() {
        return brandAbbr;
    }

    public void setBrandAbbr(String brandAbbr) {
        this.brandAbbr = brandAbbr;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
