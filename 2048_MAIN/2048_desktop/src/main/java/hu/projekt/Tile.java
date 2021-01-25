package hu.projekt;

import javafx.scene.paint.Color;


public class Tile {
    int number;

    public Tile() {
        this.number = 0;
    }

    public Tile(int number) {
        this.number = number;
    }

    public boolean isEmpty() {
        return number == 0;
    }

    public Color getCell() {
        switch (number) {
            case 2:		return Color.rgb(204, 255, 255); //238 228 218 1.0      0xeee4da
            case 4: 	return Color.rgb(77, 255, 255); //237, 224, 200, 1.0   0xede0c8
            case 8: 	return Color.rgb(0, 191, 255); //242, 177, 121, 1.0   0xf2b179
            case 16: 	return Color.rgb(0, 115, 153); //245, 149, 99, 1.0     0xf59563
            case 32: 	return Color.rgb(242, 204, 255); //246, 124, 95, 1.0     0xf67c5f
            case 64:	return Color.rgb(223, 128, 255); //246, 94, 59, 1.0      0xf65e3b
            case 128:	return Color.rgb(172, 0, 230); //237, 207, 114, 1.0   0xedcf72
            case 256: 	return Color.rgb(255, 179, 179); //237, 204, 97, 1.0     0xedcc61
            case 512: 	return Color.rgb(255, 128, 128); //237, 200, 80, 1.0     0xedc850
            case 1024: 	return Color.rgb(255, 26, 26); //237, 197, 63, 1.0     0xedc53f
            case 2048: 	return Color.rgb(102, 0, 0); //237, 194, 46, 1.0     0xedc22e
        }
        return Color.rgb(153, 153, 153); //0xcdc1b4
    }

    public Color getForeground() {
        Color foreground;
        if(number < 32) {
            foreground = Color.rgb(64, 64, 64); //0x776e65
        } else {
            foreground = Color.rgb(249, 246, 242, 1.0);    //0xf9f6f2
        }
        return foreground;
    }
}