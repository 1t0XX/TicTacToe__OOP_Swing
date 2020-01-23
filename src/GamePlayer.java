import java.util.Random;

public class GamePlayer {

    private static Random random = new Random();
    private char playerSign;
    private boolean realPlayer = true;

    public GamePlayer(boolean isRealPlayer, char playerSign) {
        this.realPlayer = isRealPlayer;
        this.playerSign = playerSign;
    }

    public boolean isRealPlayer() {
        return realPlayer;
    }

    public char getPlayerSign() {
        return playerSign;
    }



    /**
     * Ход человека
     * @param button GameButton - ссылка на ячейку поля
     */
    void updateByPlayersData(GameButton button) {

        int row = button.getButtonIndex() / GameBoard.dimension; // номер ряда
        int cell = button.getButtonIndex() % GameBoard.dimension; // номер ячейки в столбце


        //обновить матрицу игры
        button.getBoard().updateGameField(row, cell);

        //обновить содержание кнопки
        button.setText(Character.toString(button.getBoard().getGame().getCurrentPlayer().getPlayerSign()));

        if (button.getBoard().checkWin()) {
            button.getBoard().getGame().showMessage("Вы победили");
            button.getBoard().emptyField();
        } else {
            button.getBoard().getGame().passTurn();
        }

    }

    /**
     * Ход компьютера
     *
     * @param button GameButton- ссылка на ячейку поля
     */

    void updateByAiData(GameButton button) {
        //генерация координат хода компьютера
        int maxScoreRow = -1;
        int maxScoreCell = -1;
        int maxScore = 0;
        char [][] map = button.getBoard().getGameField();
        int row = -1;
        int cell = -1;


        for (int i = 0; i < GameBoard.dimension; i++) {
            for (int j = 0; j < GameBoard.dimension; j++) {
                int fieldScore = 0;

                if (map[i][j] == GameBoard.nullSymbol) {
                    //проверяем направления
                    //лево верх
                    if (i - 1 >= 0 && j - 1 >= 0 && map[i - 1][j - 1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()) {
                        fieldScore++;

                    }
                    //верх
                    if (i - 1 >= 0 && map[i - 1][j] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()){
                        fieldScore++;
                    }
                    //право верх
                    if (i - 1 >= 0 && j + 1 < GameBoard.dimension && map[i - 1][j + 1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()) {
                        fieldScore++;
                    }
                    //право
                    if (j + 1 < GameBoard.dimension && map[i][j + 1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()) {
                        fieldScore++;
                    }
                    //право низ
                    if (i + 1 < GameBoard.dimension && j + 1 < GameBoard.dimension && map[i + 1][j + 1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()) {
                        fieldScore++;
                    }
                    //низ
                    if (i + 1 < GameBoard.dimension && map[i + 1][j] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()) {
                        fieldScore++;
                    }
                }
                if (fieldScore > maxScore) {
                    maxScore = fieldScore;
                    maxScoreRow = j;
                    maxScoreCell = i;
                }
            }
        }



        //если ничего не нашли делаем глупый ход
        if(maxScoreRow == -1){
            do{
                row = random.nextInt(GameBoard.dimension);
                cell = random.nextInt(GameBoard.dimension);
            }
            while (!button.getBoard().isTurnable(row, cell));
        }



        //если в цикле найдена лучшая клетка

        if (maxScoreRow != -1) {
            row = maxScoreRow;
            cell = maxScoreCell;
        }

        //обновить матрицу игры

        button.getBoard().updateGameField(row,cell);

        //обновить содержимое кнопки

        int cellIndex = GameBoard.dimension * row + cell;
        button.getBoard().getButton(cellIndex).setText(Character.toString(button.getBoard().getGame().getCurrentPlayer().getPlayerSign()));



        //проверка на победу

        if(button.getBoard().checkWin()){
            button.getBoard().getGame().showMessage("Компьютер выиграл!");
            button.getBoard().emptyField();
        }
        else {
            //передать ход
            button.getBoard().getGame().passTurn();
        }

    }
}
