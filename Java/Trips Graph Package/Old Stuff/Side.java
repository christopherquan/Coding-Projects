package graph;


public class Side {
    private int _start;
    private int _end;

    public Side(int start, int end) {
        _start = start;
        _end = end;
    }

    int getStart() {
        return _start;
    }

    int getEnd() {
        return _end;
    }

    boolean equals(Side i) {
        return (i.getStart() == getStart()) && (i.getEnd() == getEnd());
    }

}