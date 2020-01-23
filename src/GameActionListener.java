import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class GameActionListener implements ActionListener {

    private GameButton button;

    public GameActionListener(GameButton gButton) {
       button = gButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GameBoard board = button.getBoard();

        int row = button.getButtenIndex() / GameBoard.dimension;
        int cell = button.getButtonIndex() % GameBoard.dimension;

        if (board.isTurnable(row, cell)) {

            board.getGame().getCurrentPlayer().updateByPlayersData(button);

            if (board.isFull()) {
                button.getBoard().getGame().showMessage("Ничья!");
                board.emptyField();

            } else if (!board.getGame().getCurrentPlayer().isRealPlayer()) {
                board.getGame().getCurrentPlayer().updateByAiData(button);
            }
        }

        else {
            button.getBoard().getGame().showMessage("Некорректный ход!");
        }
    }
}
