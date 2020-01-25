/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import editor.display.CharacterDisplay;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class represents the document being edited. Using a 2d array to hold the
 * document content is probably not a very good choice. Fixing this class is the
 * main focus of the exercise. In addition to designing a better data model, you
 * must add methods to do at least basic editing: write and delete text, and
 * moving the cursor
 *
 * @author evenal
 */
public class Document {

    private CharacterDisplay display;
    private int cursorRow;
    private int cursorCol;
    private char[][] data;
    LinkedList<Character> cursorC = new LinkedList<>();
    LinkedList<Character> cursorR = new LinkedList<>();

    public Document(CharacterDisplay display) {
        this.display = display;
        cursorCol = cursorRow = 0;

    }

    public void insertChar(char c) {
        System.out.println(c);
        //data[cursorRow][cursorCol] = c;
        cursorR.add(cursorRow, c);
        cursorC.add(cursorCol, c);
        display.displayChar(c, cursorRow, cursorCol);
        display.displayCursor(' ', cursorRow, cursorCol);
        if (cursorCol == 39 && cursorRow == 19) {
        } else {
            cursorCol++;
            if (cursorCol >= CharacterDisplay.WIDTH) {
                cursorCol = 0;
                cursorRow++;
            }
        }
    }

    public void removeChar(char c) {
        cursorR.remove();
        cursorC.remove();
        display.displayChar(c, cursorRow, cursorCol);
        if (cursorCol == 0 && cursorRow == 0) {
        } else if (cursorCol == 0 && cursorRow >= 0) {
            cursorCol = 39;
            cursorRow--;
        } else {
            cursorCol--;
        }
        display.displayCursor(' ', cursorRow, cursorCol);


    }

    public void shiftChar(char c) {
        cursorC.add(cursorCol, c);
        cursorR.add(cursorRow, c);

        display.displayChar(c, cursorRow, cursorCol);
        if (cursorRow == 19) {

        } else {
            cursorCol = 0;
            cursorRow++;
        }

        display.displayCursor(' ', cursorRow, cursorCol);

    }

    public void moveUp(char c) {

        if (cursorRow == 0) {

        } else if (cursorRow > 0) {
            cursorRow--;
        }
        display.displayCursor(data[cursorRow][cursorCol], cursorRow, cursorCol);
    }

    public void moveDown() {

        if (cursorRow == 19) {

        } else if (cursorRow > 19) {
            cursorRow++;
        }
        display.displayCursor(data[cursorRow][cursorCol], cursorRow, cursorCol);
    }
}
