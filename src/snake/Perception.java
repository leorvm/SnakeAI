package snake;

public class Perception {
    private Cell n, s, e, w, food;

    public Perception(Cell N, Cell S, Cell E, Cell O, Cell food) {
        this.n = N;
        this.s = S;
        this.e = E;
        this.w = O;
        this.food = food;
    }

    public Cell getE() {
        return e;
    }

    public Cell getN() {
        return n;
    }

    public Cell getW() {
        return w;
    }

    public Cell getS() {
        return s;
    }

    public Cell getFood() {
        return food;
    }
}
