package com.example.createnote.model;

/**
 * Enumeration of note categories, represented as colors.
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public enum Category {

    RED(1), ORANGE(2), YELLOW(3), GREEN(4), TEAL(5), BLUE(6), INDIGO(7), PURPLE(8);

    private int colorId;

    // create a category with a specific color ID.
    Category(int colorId) {
        this.colorId = colorId;
    }

    /**
     * Get the category's color ID.
     * @return
     */
    public int getColorId() {
        return colorId;
    }
}
