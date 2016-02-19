package bichoperdido.assyncservice.match.handlers.atributos;

import bichoperdido.assyncservice.match.domain.Axis;
import bichoperdido.assyncservice.match.domain.Semelhanca;
import bichoperdido.business.cor.CorUtils;
import bichoperdido.business.cor.domain.CieLab;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel.
 */
public class CorComparator extends ExclusivoComparator {

    private static final int PESO = 3;
    private final List<CieLab> corA;
    private final List<CieLab> corB;
    private final CorUtils corUtils;

    public CorComparator(List<CieLab> corA, List<CieLab> corB, CorUtils corUtils) {
        this.corA = corA;
        this.corB = corB;
        this.corUtils = corUtils;
    }

    @Override
    public Semelhanca compare() {
        Double[][] diffMatrix = buildDiffMatrix();
        List<Double> bestValues = new ArrayList<>();
        compare(diffMatrix, bestValues);

        double sum = 0;
        for (Double bestValue : bestValues) sum += bestValue;
        double sem = sum / bestValues.size();
        double penalties = ((double) Math.abs(diffMatrix.length - diffMatrix[0].length) * .1) * sem;
        sem -= penalties;
        return new Semelhanca(sem, PESO);
    }

    private void compare(Double[][] diffMatrix, List<Double> bestValues) {
        Axis best = getBestValuePosition(diffMatrix);
        bestValues.add(diffMatrix[best.getX()][best.getY()]);
        diffMatrix = trimColumnAndLineOfBestValue(best, diffMatrix);

        if (diffMatrix.length > 0) {
            compare(diffMatrix, bestValues);
        }
    }

    private Double[][] trimColumnAndLineOfBestValue(Axis best, Double[][] diffMatrix) {
        Double[][] newMatrix = new Double[0][];
        int xLength = diffMatrix.length - 1;
        int yLength = diffMatrix[0].length - 1;

        if (xLength > 0 && yLength > 0) {
            newMatrix = new Double[xLength][yLength];

            for (int x = 0, x1 = 0; x < diffMatrix.length; x++) {
                if (x == best.getX()) continue;
                for (int y = 0, y1 = 0; y < diffMatrix[x].length; y++) {
                    if (y == best.getY()) continue;

                    newMatrix[x1][y1++] = diffMatrix[x][y];
                }
                x1++;
            }
        }

        return newMatrix;
    }

    private Axis getBestValuePosition(Double[][] diffMatrix) {
        Axis best = new Axis(0, 0);

        for (int x = 0; x < diffMatrix.length; x++) {
            for (int y = 0; y < diffMatrix[x].length; y++) {
                if (isXYBetterThanTheBest(diffMatrix, best, x, y)
                        || (isXYEqualsToTheBest(diffMatrix, best, x, y) && isXYCloserToDiagonalThanTheBest(x, y, best))) {
                    best = new Axis(x, y);
                }
            }
        }

        return best;
    }

    private boolean isXYCloserToDiagonalThanTheBest(int x, int y, Axis best) {
        return Math.abs(best.getX() - best.getY()) > Math.abs(x - y);
    }

    private boolean isXYEqualsToTheBest(Double[][] diffMatrix, Axis best, int x, int y) {
        return diffMatrix[x][y].equals(diffMatrix[best.getX()][best.getY()]);
    }

    private boolean isXYBetterThanTheBest(Double[][] diffMatrix, Axis best, int x, int y) {
        return diffMatrix[x][y] > diffMatrix[best.getX()][best.getY()];
    }


    private Double[][] buildDiffMatrix() {
        Double[][] diffs = new Double[corA.size()][corB.size()];

        for (int x = 0; x < corA.size(); x++) {
            for (int y = 0; y < corB.size(); y++) {
                diffs[x][y] = calcDeltaEGrau(corA.get(x), corB.get(y));
            }
        }

        return diffs;
    }

    private double calcDeltaEGrau(CieLab corA, CieLab corB) {
        return toGrau(corUtils.deltaE(corA, corB));
    }

    private double toGrau(double deltaE) {
        if (deltaE < 50) {
            return (50 - deltaE) / 50;
        }

        return .0;
    }
}
