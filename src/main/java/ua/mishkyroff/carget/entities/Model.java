package ua.mishkyroff.carget.entities;

/**
 * A Model entity stores information about car's models information
 * The entity has its representation in the database
 *
 * @author Anton Mishkyroff
 */
public class Model {

    private int idModel;
    private Brand brand;
    private String className;
    private String modelName;
    private int doorsQty;
    private boolean automat;
    private double power;
    private boolean condition;
    private String img;


    public Model(Brand brand, String className, String modelName, int doorsQty, boolean automat,
                 double power, boolean condition, String img) {
        this.brand = brand;
        this.className = className;
        this.modelName = modelName;
        this.doorsQty = doorsQty;
        this.automat = automat;
        this.power = power;
        this.condition = condition;
        this.img = img;
    }

    public Model(int idModel, Brand brand, String className, String modelName, int doorsQty, boolean automat,
                 double power, boolean condition, String img) {
        this(brand, className, modelName, doorsQty, automat, power, condition, img);
        this.idModel = idModel;
    }

    @Override
    public String toString() {
        return "MODEL (idModel=" + idModel + " brand=" + brand + " class=" + className +
                " modelName=" + modelName + " doors qty=" + doorsQty + " automat=" + automat +
                " power=" + power + " conditioning=" + condition + ")";
    }

    public int getIdModel() {
        return idModel;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getDoorsQty() {
        return doorsQty;
    }

    public void setDoorsQty(int doorsQty) {
        this.doorsQty = doorsQty;
    }

    public boolean getAutomat() {
        return automat;
    }

    public void setAutomat(boolean automat) {
        this.automat = automat;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public boolean getCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setImg(String img) {

        this.img = img == null ? "/res/no-image.png" : img;
    }

    public String getImg() {
        return img;
    }
}
