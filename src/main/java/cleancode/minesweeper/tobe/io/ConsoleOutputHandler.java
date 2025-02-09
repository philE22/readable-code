package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;
import cleancode.minesweeper.tobe.position.CellPosition;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler{

    private static final String EMPTY_CELL_SIGN = "■";
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_CELL_SIGN = "□";

    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showBoard(GameBoard board) {
        String colAlphabets = generateColAlphabets(board);
        System.out.println("    " + colAlphabets);
        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {
                CellPosition cellPosition = CellPosition.of(row, col);

                CellSnapshot snapshot = board.getSnapshot(cellPosition);
                String cellSign = decideCellSignFrom(snapshot);

                System.out.print(cellSign + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private String decideCellSignFrom(CellSnapshot snapshot) {
        CellSnapshotStatus status = snapshot.getStatus();

        if (status == CellSnapshotStatus.EMPTY) {
            return EMPTY_CELL_SIGN;
        }
        if (status == CellSnapshotStatus.FLAG) {
            return FLAG_SIGN;
        }
        if (status == CellSnapshotStatus.LAND_MINE) {
            return LAND_MINE_SIGN;
        }
        if (status == CellSnapshotStatus.UNCHECKED) {
            return UNCHECKED_CELL_SIGN;
        }
        if (status == CellSnapshotStatus.NUMBER) {
            return String.valueOf(snapshot.getNearbyLandMineCount());
        }
        throw new IllegalArgumentException("확인할 수 없는 셀입니다");
    }

    private String generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())
                .mapToObj(index -> (char) ('a' + index))
                .map(Object::toString)
                .toList();
        String joiningAlphabets = String.join(" ", alphabets);
        return joiningAlphabets;
    }

    @Override
    public void printGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void printGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void printCommentForSelectingCell() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void printCommentForUserAction() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void printExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void printSimpleMessage(String message) {
        System.out.println(message);
    }
}
