package ua.mishkyroff.carget.entities;

/**
 * A Brand entity stores information about car's brand
 * The entity has its representation in the database
 *
 * @author Anton Mishkyroff
 */
public class Brand {
    private Integer idBrand;
    private String brandAbbr;
    private String fullName;

    public Brand(String brandAbbr, String fullName) {
        this.brandAbbr = brandAbbr;
        this.fullName = fullName;
    }

    public Brand(int idBrand, String brandAbbr, String fullName) {
        this(brandAbbr, fullName);
        this.idBrand = idBrand;
    }

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
