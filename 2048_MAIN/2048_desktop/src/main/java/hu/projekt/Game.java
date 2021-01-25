package hu.projekt;

import hu.projekt.dao.ScoreDAO;
import hu.projekt.dao.ScoreDAOImpl;
import hu.projekt.model.Score;

import java.util.List;
import java.util.*;

public class Game extends javafx.scene.canvas.Canvas  {

    private Tile[] tiles;
    protected int size;
    protected boolean win = false;
    protected boolean lose = false;
    protected int score = 0;

    public Tile[] getTiles() {
        return tiles;
    }

    public Game(int size) {
        super((size*100), (size*100)+100);
        this.size = size;
        setFocused(true);
        resetGame();
    }

    public Game(double width, double height) {
        super(width, height);
        setFocused(true);
        resetGame();
    }

    void resetGame() {
        if (score > 0) {
            ScoreDAO dao = new ScoreDAOImpl();
            Score asd = new Score(score,"0");
            dao.addScore(asd);
        }

        score = 0;
        win = false;
        lose = false;
        tiles = new Tile[size * size];
        for (int cell = 0; cell < tiles.length; cell++) {
            tiles[cell] = new Tile();
        }
        addCell();
        addCell();
    }

    private void addCell() {
        List<Tile> list = availableSpace();
        if(!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Tile emptyTile = list.get(index);
            emptyTile.number = Math.random() < 0.9 ? 2 : 4;
        }

    }

    private List<Tile> availableSpace() {
        List<Tile> list = new ArrayList<>(size*size);
        for(Tile c : tiles)
            if(c.isEmpty())
                list.add(c);
        return list;
    }

    private boolean isFull() {
        return availableSpace().size() == 0;
    }

    private Tile cellAt(int x, int y) {
        return tiles[x + y * size];
    }

    protected boolean canMove() {
        if(!isFull()) return true;
        for(int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tile tile = cellAt(x, y);
                if ((x < size-1 && tile.number == cellAt(x + 1, y).number) ||
                        (y < size-1) && tile.number == cellAt(x, y + 1).number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compare(Tile[] line1, Tile[] line2) {
        if(line1 == line2) {
            return true;
        }
        if (line1.length != line2.length) {
            return false;
        }

        for(int i = 0; i < line1.length; i++) {
            if(line1[i].number != line2[i].number) {
                return false;
            }
        }
        return true;
    }

    private Tile[] rotate(int angle) {
        Tile[] tiles = new Tile[size * size];
        int offsetX = size-1;
        int offsetY = size-1;
        if(angle == 90) {
            offsetY = 0;
        } else if(angle == 270) {
            offsetX = 0;
        }

        double rad = Math.toRadians(angle);
        int cos = (int) Math.cos(rad);
        int sin = (int) Math.sin(rad);
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                int newX = (x*cos) - (y*sin) + offsetX;
                int newY = (x*sin) + (y*cos) + offsetY;
                tiles[(newX) + (newY) * size] = cellAt(x, y);
            }
        }
        return tiles;
    }

    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for(int i = 0; i < size; i++) {
            if(!oldLine[i].isEmpty()){
                list.addLast(oldLine[i]);
            }
        }

        if(list.size() == 0) {
            return oldLine;
        } else {
            Tile[] newLine = new Tile[size];
            while (list.size() != size) {
                list.add(new Tile());
            }
            for(int j = 0; j < size; j++) {
                newLine[j] = list.removeFirst();
            }
            return newLine;
        }
    }

    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for(int i = 0; i < size && !oldLine[i].isEmpty(); i++) {
            int num = oldLine[i].number;
            if (i < size-1 && oldLine[i].number == oldLine[i+1].number) {
                num *= 2;
                score += num;
                if ( num == 2048) {
                    win = true;
                }
                i++;
            }
            list.add(new Tile(num));
        }

        if(list.size() == 0) {
            return oldLine;
        } else {
            while (list.size() != size) {
                list.add(new Tile());
            }
            return list.toArray(new Tile[size]);
        }
    }

    private Tile[] getLine(int index) {
        Tile[] result = new Tile[size];
        for(int i = 0; i < size; i++) {
            result[i] = cellAt(i, index);
        }
        return result;
    }

    private void setLine(int index, Tile[] re) {
        System.arraycopy(re, 0, tiles, index * size, size);
    }

    public void left() {
        boolean needAddCell = false;
        for(int i = 0; i < size; i++) {
            Tile[] line = getLine(i);
            Tile[] merged = mergeLine(moveLine(line));
            setLine(i, merged);
            if( !needAddCell && !compare(line, merged)) {
                needAddCell = true;
            }
        }
        if(needAddCell) {
            addCell();
        }
    }

    public void right() {
        tiles = rotate(180);
        left();
        tiles = rotate(180);
    }

    public void up() {
        tiles = rotate(270);
        left();
        tiles = rotate(90);
    }

    public void down() {
        tiles = rotate(90);
        left();
        tiles = rotate(270);
    }


    //#################################### getter setter


    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}