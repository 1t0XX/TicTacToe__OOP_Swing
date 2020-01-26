import javax.swing.*;

public class GameButton extends JButton {

    private int buttonIndex;
    private GameBoard board;

    public GameButton(int gameButtonIndex, GameBoard currentGameBoard) {
        buttonIndex = gameButtonIndex;
        board = currentGameBoard;

        int row  = buttonIndex / GameBoard.dimension; // номер ряда
        int cell = buttonIndex % GameBoard.dimension; // номер ячейки в столбце

        setSize(GameBoard.cellSize - 5, GameBoard.cellSize - 5);
        addActionListener(new GameActionListener(this));
    }

    public GameBoard getBoard() {
        return board;
    }

    public int getButtonIndex() {
        return buttonIndex;
    }
}

