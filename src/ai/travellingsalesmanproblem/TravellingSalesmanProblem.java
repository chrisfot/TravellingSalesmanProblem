package ai.travellingsalesmanproblem;

/**
 * Created by Chris on 17/05/2016.
 */
public class TravellingSalesmanProblem {

    private static final City[] cities = {new City("A"), new City("B"), new City("C"), new City("D"), new City("E")};

    private static final int[][] distances = {
            {0, 4, 4, 7, 3},
            {4, 0, 2, 3, 5},
            {4, 2, 0, 2, 3},
            {7, 3, 2, 0, 6},
            {3, 5, 3, 6, 0}
    };



    public TravellingSalesmanProblem() {
    }

    private static int getCityIndex(City city) {
        for (int i = 0; i < cities.length; i++) {
            if (city.equals(cities[i])) {
                return i;
            }
        }
        return -1; // Out of bounds; not likely to ever happen
    }

    public static City getCity(int index){
        return (index >= 0 && index < cities.length) ? cities[index] : null;
    }

    public static City[] getCities() {
        return cities;
    }

    public static int getNumberOfCities() {
        return cities.length;
    }

    public static int getDistance(City from, City to) {
        return distances[getCityIndex(from)][getCityIndex(to)];
    }

}
