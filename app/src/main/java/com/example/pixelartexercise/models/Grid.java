package com.example.pixelartexercise.models;

/**
 * Created by prebe on 13/11/2017.
 */

public class Grid {
    private static Grid gridInstance;
    private int gridSize;
    private Pixel[][] grid;

    private Grid(int gridSize){
        setGridSize(gridSize);
        grid = new Pixel[gridSize][gridSize];

        for(int i = 0; i<gridSize; i++){
            for(int j = 0; j<gridSize; j++){
                grid[i][j] = new Pixel();
            }
        }
    }

    public static Grid get(int gridSize){
        if(gridInstance == null || gridInstance.getGridSize() > gridSize)
            gridInstance = new Grid(gridSize);
        return gridInstance;
    }

    private void setGridSize(int gridSize) {
        if(gridSize < 4)
            throw new IllegalArgumentException("Grid moet groter zijn dan 4X4");
        else if(gridSize > 50)
            throw new IllegalArgumentException("Grid moet kleiner zijn dan 50X50");
        else
            this.gridSize = gridSize;
    }

    public int getGridSize() {
        return gridSize;
    }

    public Pixel getPixel(int i, int j) {
        return grid[i][j];
    }
}
