package tetris;

class UnreachableCode extends Exception {
    UnreachableCode() {
        super("Reached a segment of code that should be unreachable");
    }

}
