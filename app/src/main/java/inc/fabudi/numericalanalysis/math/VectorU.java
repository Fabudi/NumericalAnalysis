package inc.fabudi.numericalanalysis.math;

public class VectorU {
    public Double[] coordinates;
    int size;

    VectorU() {
        size = 3;
        coordinates = new Double[]{0.0, 0.0, 0.0};
    }

    public VectorU(int size) {
        this.size = size;
    }

    public VectorU(int size, Double[] coordinates) {
        this.size = size;
        this.coordinates = coordinates.clone();
    }

    @Override
    protected Object clone() {
        return new VectorU(this.size, this.coordinates.clone());
    }

    public VectorU subtractVector(VectorU vectorU){
        Double[] newCoords = new Double[vectorU.size];
        for(int i = 0; i < vectorU.size; i++){
            newCoords[i]=getCoordinateByIndex(i)- vectorU.getCoordinateByIndex(i);
        }
        return new VectorU(vectorU.size, newCoords);
    }

    public void subtractCoordinate(double number, int index) {
        coordinates[index] -= number;
    }


    public double getCoordinateByIndex(int index) {
        return coordinates[index];
    }

    public void setCoordinateByIndex(Double coordinate, int index) {
        this.coordinates[index] = coordinate;
    }
}
