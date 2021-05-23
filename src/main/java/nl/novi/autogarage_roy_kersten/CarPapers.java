package nl.novi.autogarage_roy_kersten;

public class CarPapers {

    //Attributes
    private int idPathName;
    private String pathName;
    private Car car;

    //Constructors
    public CarPapers(int idPathName, String pathName, Car car) {
        this.idPathName = idPathName;
        this.pathName = pathName;
        this.car = car;
    }

    //Getters and Setters

    public int getIdPathName() {
        return idPathName;
    }

    public void setIdPathName(int idPathName) {
        this.idPathName = idPathName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
