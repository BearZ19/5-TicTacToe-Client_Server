package FindingConnectedComponentsinaGraphAlgorithm_Java;

import java.util.*;

public class BFSConnectedFive implements FindingConnectedComponentsinaGraphAlgorithm_Java.IAlgo_Shortest_Path_in_a_Graph {

    private final int[][] directions = {
            {1, 0}, {-1, 0},   // vertical
            {0, 1}, {0, -1},   // horizontal
            {1, 1}, {-1, -1},  // diagonal \
            {1, -1}, {-1, 1}   // diagonal /
    };

    @Override
    public boolean hasConnectedFive(String[][] board, int row, int col, String player) {
        for (int d = 0; d < directions.length; d += 2) {
            int count = 1;
            count += bfs(board, row, col, player, directions[d][0], directions[d][1]);
            count += bfs(board, row, col, player, directions[d + 1][0], directions[d + 1][1]);
            if (count >= 5) return true;
        }
        return false;
    }

    private int bfs(String[][] board, int row, int col, String player, int dx, int dy) {
        int count = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row + dx, col + dy});

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];

            if (r >= 0 && c >= 0 && r < board.length && c < board[0].length && player.equals(board[r][c])) {
                count++;
                queue.add(new int[]{r + dx, c + dy});
            }
        }

        return count;
    }
}
