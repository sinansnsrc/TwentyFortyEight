import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;

public class TwentyFortyEight extends PApplet {
    public Tile[][] tiles = new Tile[4][4];
    public ArrayList<Tile> offGrid = new ArrayList<Tile>();
    public ArrayList<Tile[][]> previousState;
    public long score;

    public static void main(String[] args) {
        PApplet.main(new String[] {"TwentyFortyEight"});
    }

    public void settings() {
        size(400, 475);
        smooth(8);

    }

    public void setup() {
        int row =(int) (Math.random() * 4);
        int col =(int) (Math.random() * 4);

        tiles[row][col] = new Tile(new int[] {row, col}, this);

        while(tiles[row][col] != null) {
            row =(int) (Math.random() * 4);
            col =(int) (Math.random() * 4);
        }

        tiles[row][col] = new Tile(new int[] {row, col}, this);

        score = 0;
        previousState = new ArrayList<Tile[][]>();

        background(250,250,250);
        frameRate(60);
    }

    public void draw() {
        displayBackdrop();
        displayScore();
        displayBoard();
        if(gameOver()) {
            displayEndScreen();
        }
    }

    public void displayBackdrop() {
        clear();
        background(250, 248, 239);
        noStroke();
        fill(187,173,160);
        rect(25, 25, 162.5F, 50, 4);
        rect(212.5F, 25, 162.5F, 50, 4);
        rect(25, 100, 350, 350, 4);
    }

    public void displayBoard() {
        fill(192,192,192);
        noStroke();
        for(int i = 0; i < offGrid.size(); i++) {
            if(offGrid.get(i).lerpToPosition()) {
                offGrid.get(i).display();
                offGrid.remove(i);
                i--;
            }
            else {
                offGrid.get(i).display();
            }
        }
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if(tiles[row][col] != null) {
                    tiles[row][col].lerpToPosition();
                    tiles[row][col].display();
                }
            }
        }
    }

    public void displayScore() {
        textAlign(CENTER, CENTER);
        fill(255,255,255);
        textSize(32);
        text("Score:", 25F, 21.5F, 162.5F, 50F);
        text(score + "", 212.5F, 21.5F, 162.5F, 50F);
    }

    public void displayEndScreen() {
        fill(250, 248, 239, 215);
        rect(0,0, 400F, 475F);
        fill(187,173,160);
        textAlign(CENTER, CENTER);
        textSize(50);
        text("Game Over!", 200, 75);
        text("Final Score:", 200, 225);
        text(score+"", 200, 275);
        textSize(30);
        text("Press Any Key To Restart", 200, 375);
    }

    public void keyPressed() {
        if(gameOver()) {
            resetGame();
        }
        else {
            long moveScore = 0;
            captureState();
            if(key == 'w' || (key == CODED && keyCode == UP)) {
                completeProcesses();
                for(int row = 0; row < 4; row++) {
                    for(int col = 0; col < 3; col++) {
                        while(tiles[row][col] == null && tiles[row][col + 1] != null) {
                            tiles[row][col + 1].setTargetPosition(new int[] {row, col}, false);
                            tiles[row][col] = tiles[row][col + 1];
                            tiles[row][col + 1] = null;
                            if(col > 0) {
                                col--;
                            }
                            continue;
                        }

                        if(tiles[row][col] != null && tiles[row][col + 1] != null) {
                            if(tiles[row][col].getValue() == tiles[row][col + 1].getValue()  && !tiles[row][col].getRecentlyMerged() && !tiles[row][col + 1].getRecentlyMerged() ) {
                                tiles[row][col + 1].setTargetPosition(new int[] {row, col}, true);
                                tiles[row][col].incrementValue();
                                offGrid.add(tiles[row][col + 1]);
                                tiles[row][col + 1] = null;
                                tiles[row][col].setRecentlyMerged(true);
                                moveScore += tiles[row][col].getValue();
                            }
                        }
                    }
                }
            }
            else if(key == 'a' || (key == CODED && keyCode == LEFT)) {
                completeProcesses();
                for(int row = 0; row < 3; row++) {
                    for(int col = 0; col < 4; col++) {
                        while(tiles[row][col] == null && tiles[row + 1][col] != null) {
                            tiles[row + 1][col].setTargetPosition(new int[] {row, col}, false);
                            tiles[row][col] = tiles[row + 1][col];
                            tiles[row + 1][col] = null;
                            if(row > 0) {
                                row--;
                            }
                            continue;
                        }

                        if(tiles[row][col] != null && tiles[row + 1][col] != null) {
                            if(tiles[row][col].getValue() == tiles[row + 1][col].getValue()  && !tiles[row][col].getRecentlyMerged() && !tiles[row + 1][col].getRecentlyMerged() ) {
                                tiles[row + 1][col].setTargetPosition(new int[] {row, col}, true);
                                tiles[row][col].incrementValue();
                                offGrid.add(tiles[row + 1][col]);
                                tiles[row + 1][col] = null;
                                tiles[row][col].setRecentlyMerged(true);
                                moveScore += tiles[row][col].getValue();
                            }
                        }
                    }
                }
            }
            else if(key == 's' || (key == CODED && keyCode == DOWN)) {
                completeProcesses();
                for(int row = 0; row < 4; row++) {
                    for(int col = 3; col > 0; col--) {
                        while(tiles[row][col] == null && tiles[row][col - 1] != null) {
                            tiles[row][col - 1].setTargetPosition(new int[] {row, col}, false);
                            tiles[row][col] = tiles[row][col - 1];
                            tiles[row][col - 1] = null;
                            if(col < 3) {
                                col++;
                            }
                            continue;
                        }

                        if(tiles[row][col] != null && tiles[row][col - 1] != null) {
                            if(tiles[row][col].getValue() == tiles[row][col - 1].getValue()  && !tiles[row][col].getRecentlyMerged() && !tiles[row][col - 1].getRecentlyMerged() ) {
                                tiles[row][col - 1].setTargetPosition(new int[] {row, col}, true);
                                tiles[row][col].incrementValue();
                                offGrid.add(tiles[row][col - 1]);
                                tiles[row][col - 1] = null;
                                tiles[row][col].setRecentlyMerged(true);
                                moveScore += tiles[row][col].getValue();
                            }
                        }
                    }
                }
            }
            else if(key == 'd' || (key == CODED && keyCode == RIGHT)) {
                completeProcesses();
                for(int row = 3; row > 0; row--) {
                    for(int col = 0; col < 4; col++) {
                        while(tiles[row][col] == null && tiles[row - 1][col] != null) {
                            tiles[row - 1][col].setTargetPosition(new int[] {row, col}, false);
                            tiles[row][col] = tiles[row - 1][col];
                            tiles[row - 1][col] = null;
                            if(row < 3) {
                                row++;
                            }
                            continue;
                        }

                        if(tiles[row][col] != null && tiles[row - 1][col] != null) {
                            if(tiles[row][col].getValue() == tiles[row - 1][col].getValue() && !tiles[row][col].getRecentlyMerged() && !tiles[row - 1][col].getRecentlyMerged() ) {
                                tiles[row - 1][col].setTargetPosition(new int[] {row, col}, true);
                                tiles[row][col].incrementValue();
                                offGrid.add(tiles[row - 1][col]);
                                tiles[row - 1][col] = null;
                                tiles[row][col].setRecentlyMerged(true);
                                moveScore += tiles[row][col].getValue();
                            }
                        }
                    }
                }
            }
            else if(key == 'r') {
                resetGame();
                return;
            }
            else {
                return;
            }

            spawn();
            score += moveScore;
        }
    }

    public void resetGame() {
        tiles = new Tile[4][4];
        offGrid.clear();
        int row =(int) (Math.random() * 4);
        int col =(int) (Math.random() * 4);

        tiles[row][col] = new Tile(new int[] {row, col}, this);

        while(tiles[row][col] != null) {
            row =(int) (Math.random() * 4);
            col =(int) (Math.random() * 4);
        }

        tiles[row][col] = new Tile(new int[] {row, col}, this);

        score = 0;
    }

    private void captureState() {
        Tile[][] capturedState = new Tile[4][4];

        for (int i = 0; i < 4; i++) {
            capturedState[i] = Arrays.copyOf(tiles[i], 4);
        }

        previousState.add(capturedState);
    }

    private void spawn() {
        Tile[][] initialState = previousState.get(previousState.size() - 1);
        boolean changed = false;
        for (int row = 0; row < 4 && !changed; row++) {
            for (int col = 0; col < 4 && !changed; col++) {
                if(initialState[row][col] != null && tiles[row][col] == null
                        || initialState[row][col] == null && tiles[row][col] != null
                        || (initialState[row][col] != null && tiles[row][col] != null
                        && initialState[row][col].getValue() != tiles[row][col].getValue())) {
                    changed = true;
                }
            }
        }

        if(changed) {
            boolean spawned = false;
            while(!spawned) {
                int row = (int) (Math.random() * 4);
                int col = (int) (Math.random() * 4);
                if(tiles[row][col] == null) {
                    tiles[row][col] = new Tile(new int[] {row, col}, this);
                    spawned = true;
                }
            }
        }
    }

    private boolean gameOver() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if(tiles[row][col] == null) {
                    return false;
                }
            }
        }

        for(int row = 3; row > 0; row--) {
            for(int col = 0; col < 4; col++) {
                if(tiles[row][col] != null && tiles[row - 1][col] != null) {
                    if(tiles[row][col].getValue() == tiles[row - 1][col].getValue()) {
                        return false;
                    }
                }
            }
        }

        for(int row = 0; row < 4; row++) {
            for(int col = 3; col > 0; col--) {
                if(tiles[row][col] != null && tiles[row][col - 1] != null) {
                    if(tiles[row][col].getValue() == tiles[row][col - 1].getValue()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void completeProcesses() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if(tiles[row][col] != null) {
                    tiles[row][col].completeLerp();
                    tiles[row][col].setRecentlyMerged(false);
                }
            }
        }
    }
}
