package com.example;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "_________";
        String[] cellValues = input.split("");
        printGameBoard(cellValues);
        String emptyCell = "_";
        String gameEndingStates = "125";
        String[] gameStates = {"Impossible", "X wins", "O wins", "Game not finished", "Draw"};

        for (int i = 0; !gameEndingStates.contains(String.valueOf(getState(cellValues))); i++) {

            while (true) {
                System.out.print("Enter the coordinates: ");

                String[] coordinates = scanner.nextLine().split(" ");

                String message = "";
                try {
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);

                    if ((x > 3 || x < 1) || (y > 3 || y < 1)) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    System.out.println(message);

                    int actualIndex = 0;

                    if (x == 1) {
                        actualIndex = y - x;
                    } else if (x == 2) {
                        actualIndex = y + x;
                    } else {
                        actualIndex = x + (2 + y);
                    }

                    if (!cellValues[actualIndex].equals(emptyCell)) {
                        message = "This cell is occupied! Choose another one!";
                    } else {
                        String playerSymbol = i % 2 == 0 ? "X" : "O";
                        cellValues[actualIndex] = playerSymbol;
                        break;
                    }


                } catch (IllegalArgumentException ignored) {
                    message = "You should enter numbers!";
                } catch (ArrayIndexOutOfBoundsException thrown) {
                    message = "Coordinates should be from 1 to 3!";
                }

                System.out.println(message);
            }

            printGameBoard(cellValues);
        }

        System.out.println(gameStates[getState(cellValues)]);

    }

    private static int getState(String[] cellValues) {
        boolean xWins = hasWon("X", cellValues);
        boolean oWins = hasWon("O", cellValues);
        int numberOfXOnBoard = numberOnBoard("X", cellValues);
        int numberOfOonBoard = numberOnBoard("O", cellValues);
        int difference = numberOfOonBoard - numberOfXOnBoard;

        StringBuilder value = new StringBuilder();

        for (String val : cellValues) {
            value.append(val);
        }

        if ((xWins && oWins) || (difference > 1 || difference < -1)) {
            return 0;
        } else if (xWins) {
            return 1;
        } else if (oWins) {
            return 2;
        } else if (containsEmptyCells("_", value.toString())) {
            return 3;
        }
        return 4;
    }

    private static boolean containsEmptyCells(String emptyValue, String input) {
        return input.contains(emptyValue);
    }

    private static int numberOnBoard(String symbol, String[] cellValues) {
        int count = 0;
        for (String cellValue : cellValues) {
            if (cellValue.equals(symbol)) {
                count += 1;
            }
        }
        return count;
    }

    private static boolean hasWon(String player, String[] cellValues) {
        // check Rows
        for (int i = 0; i <= 6; i += 3) {
            if (cellValues[i].equals(player) && cellValues[i + 1].equals(player) && cellValues[i + 2].equals(player)) {
                return true;
            }
        }

        // check Columns
        for (int i = 0; i < 3; i++) {
            if (cellValues[i].equals(player) && cellValues[i + 3].equals(player) && cellValues[i + 6].equals(player)) {
                return true;
            }
        }

        // check diagonals
        if (cellValues[2].equals(player) && cellValues[4].equals(player) && cellValues[6].equals(player)) {
            return true;
        }

        return cellValues[0].equals(player) && cellValues[4].equals(player) && cellValues[8].equals(player);
    }

    private static void printGameBoard(String[] cellValues) {
        System.out.println("---------");

        IntStream.range(0, cellValues.length).mapToObj(i -> {
            if (i % 3 == 0) {
                return "| " + cellValues[i];
            } else if ((i % 2 == 0 || i % 5 == 0) && i != 4) {
                return cellValues[i] + " |\n";
            }

            return " " + cellValues[i] + " ";
        }).forEach(System.out::print);
        System.out.println("---------");
    }
}
