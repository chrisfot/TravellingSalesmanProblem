package ai.travellingsalesmanproblem;

/**
 * Created by Chris on 17/05/2016.
 */
public class City {

    private String name;



    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public boolean equals(City city) {
        return (city instanceof City && city.getName().equals(this.getName()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
