package sample;


import java.util.ArrayList;

public class Matrix<T>{

    private ArrayList<ArrayList<T>> matrix;

    public Matrix(){
        matrix = new ArrayList<>();
    }

    public T getCell(int row, int col) {
        return matrix.get(row).get(col);
    }

    public void addRowAbove(){

    }

    public void addRowBelow(){

    }

    public void addColumnBefore(){

    }

    public void addColumnAfter(){

    }

    public void setRow(){

    }

    public void setColumn(){

    }
}
