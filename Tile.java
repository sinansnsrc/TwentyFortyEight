import processing.core.PApplet;

public class Tile {
    private int value;
    private float[] screenPosition;
    private float[] targetPosition;
    private float[] distanceToTarget;
    private int lerpIteration;
    private int sizeIteration;
    private float size;
    private PApplet sketch;
    private boolean destroyOnReachTarget;
    private boolean recentlyMerged;
    private int[][] tileColors;

    public Tile(int[] gridPosition, PApplet sketch) {
        if (Math.random() <= 0.1) {
            value = 4;
        }
        else {
            value = 2;
        }
        screenPosition = new float[] {27.5F + (gridPosition[0] * 86.875F), 102.5F + (gridPosition[1] * 86.875F)};
        this.sketch = sketch;
        targetPosition = screenPosition;
        distanceToTarget = new float[] {screenPosition[0] - targetPosition[0], screenPosition[1] - targetPosition[1]};
        recentlyMerged = false;
        size = 44.375F;
        sizeIteration = 0;
        tileColors = new int[][] {{238, 228,218}, {237, 224, 200}, {242, 177, 121}, {245, 149, 99}, {246, 124, 95}, {246, 94, 59}, {237, 207, 114}, {237, 204, 97}, {237, 200, 80}, {237, 197, 63}, {237, 194, 46}};
    }

    public int getValue() {
        return value;
    }

    public void incrementValue() {
        this.value = value * 2;
    }

    public boolean getRecentlyMerged() {
        return recentlyMerged;
    }

    public void setRecentlyMerged(boolean recentlyMerged) {
        this.recentlyMerged = recentlyMerged;
    }

    public boolean lerpToPosition() {
        if(sizeIteration < 10) {
            sizeIteration++;
            size += 4F;
        }
        if(lerpIteration < 10) {
            lerpIteration++;
            screenPosition[0] -= distanceToTarget[0] * 0.1;
            screenPosition[1] -= distanceToTarget[1] * 0.1;
        }
        else {
            return destroyOnReachTarget;
        }
        return false;
    }

    public void completeLerp() {
        size = 84.375F;
        sizeIteration = 20;
        screenPosition = targetPosition;
        lerpIteration = 20;
    }

    public void setTargetPosition(int[] gridPosition, boolean destroyOnReachTarget) {
        this.destroyOnReachTarget = destroyOnReachTarget;
        lerpIteration = 0;
        targetPosition = new float[] {27.5F + (gridPosition[0] * 86.875F), 102.5F + (gridPosition[1] * 86.875F)};
        distanceToTarget = new float[] {screenPosition[0] - targetPosition[0], screenPosition[1] - targetPosition[1]};
    }

    public void display() {
        if(value == 2) {
            sketch.fill(tileColors[0][0], tileColors[0][1], tileColors[0][2]);
        }
        else if(value == 4) {
            sketch.fill(tileColors[1][0], tileColors[1][1], tileColors[1][2]);
        }
        else if(value == 8) {
            sketch.fill(tileColors[2][0], tileColors[2][1], tileColors[2][2]);
        }
        else if(value == 16) {
            sketch.fill(tileColors[3][0], tileColors[3][1], tileColors[3][2]);
        }
        else if(value == 32){
            sketch.fill(tileColors[4][0], tileColors[4][1], tileColors[4][2]);
        }
        else if(value == 64) {
            sketch.fill(tileColors[5][0], tileColors[5][1], tileColors[5][2]);
        }
        else if(value == 128) {
            sketch.fill(tileColors[6][0], tileColors[6][1], tileColors[6][2]);
        }
        else if(value == 256) {
            sketch.fill(tileColors[7][0], tileColors[7][1], tileColors[7][2]);
        }
        else if(value == 512) {
            sketch.fill(tileColors[8][0], tileColors[8][1], tileColors[8][2]);
        }
        else if(value == 1024) {
            sketch.fill(tileColors[9][0], tileColors[9][1], tileColors[9][2]);
        }
        else{
            sketch.fill(tileColors[10][0], tileColors[10][1], tileColors[10][2]);
        }

        sketch.rect(screenPosition[0] + ((84.375F - size) / 2), screenPosition[1] + ((84.375F - size) / 2), size, size, 4);

        if(value <= 8) {
            sketch.fill(119, 110, 101);
        }
        else {
            sketch.fill(249, 246, 242);
        }
        sketch.textAlign(sketch.CENTER, sketch.CENTER);
        if(value > 65536) {
            sketch.textSize(18);
        }
        else if(value > 8192) {
            sketch.textSize(22);
        }
        else if(value > 512) {
            sketch.textSize(26);
        }
        else if(value > 64) {
            sketch.textSize(30);
        }
        else {
            sketch.textSize(32);
        }

        sketch.text(value+"", screenPosition[0], screenPosition[1],84.375F, 84.375F);
    }
}
