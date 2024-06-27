package model;

public class Line {
    private Point p1, p2;
    private double slope;
    private Region region;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;

        slope = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());

        if (slope > 0) {
            if (p2.getY() < p1.getY()) {
                region = Region.A;
            }

            else {
                region = Region.C;
            }
        }

        else {
            if (p2.getY() < p1.getY()) {
                region = Region.B;
            }

            else {
                region = Region.D;
            }
        }
    }

    public Region getRegion() {
        return region;
    }
}
