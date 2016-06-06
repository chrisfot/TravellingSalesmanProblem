package ai.travellingsalesmanproblem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chris on 17/05/2016.
 */
public class Path {

    private int length = 0;

    private ArrayList<City> path = new ArrayList<>();



    public Path(boolean initialize) {
        this.path.ensureCapacity(TravellingSalesmanProblem.getNumberOfCities());

        if (initialize) {
            this.generateRandomPath();
        } else {
            for (int i = 0; i < TravellingSalesmanProblem.getNumberOfCities(); i++) {
                this.path.add(null);
            }
        }
    }

    public void setCity(City city) {
        this.path.add(city);
    }

    public void setCity(int index, City city) {
        this.path.set(index, city);
        this.length = 0; // Clear cache
    }

    public City getCity(int i) {
        return (i >= 0 && i < this.path.size()) ? this.path.get(i) : null;
    }

    public int size() {
        return this.path.size();
    }

    public boolean contains(City city) {
        return this.path.contains(city);
    }

    public int getLength() {
        if (this.length == 0) {
            this.calculateDistance();
        }
        return this.length;
    }

    public void swapCities(int cityIndex1, int cityIndex2) {
        if ((cityIndex1 >= 0 && cityIndex1 < this.path.size()) && (cityIndex2 >= 0 && cityIndex2 < this.path.size())) {
            Collections.swap(this.path, cityIndex1, cityIndex2);
        }
    }

    private void generateRandomPath() {
        for (City city : TravellingSalesmanProblem.getCities()) {
            this.setCity(city);
        }
        Collections.shuffle(this.path);
    }

    private void calculateDistance() {
        City from, to;
        int totalCities = TravellingSalesmanProblem.getNumberOfCities();

        for (int i = 0; i < totalCities; i++) {
            from = this.path.get(i);
            to = (i+1 == totalCities) ? this.path.get(0) : this.path.get(i+1);
            this.length += TravellingSalesmanProblem.getDistance(from, to);
        }
    }

    @Override
    public String toString() {
        String path = "";

        for (City city : this.path) {
            path += city + ", ";
        }

        return "Path: " + path.substring(0, path.length()-2) + " (" + this.getLength() + ")";
    }
}
