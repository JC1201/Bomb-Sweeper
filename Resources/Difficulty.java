public enum Difficulty {

    EASY(16, 16, 15),     // 9x9 grid, ~10% bomb chance
    MEDIUM(24, 24, 20), // 16x16 grid, ~15% bomb chance
    HARD(30, 30, 25);   // 24x24 grid, ~20% bomb chance

    public final int width;
    public final int height;
    public final int mineProbability;

    Difficulty(int width, int height, int mineProbability) {
        this.width = width;
        this.height = height;
        this.mineProbability = mineProbability;
    }
}

