package com.assessment;

import com.assessment.model.InputConfig;
import com.assessment.model.Output;
import com.assessment.model.probabilities.BonusProbability;
import com.assessment.model.probabilities.BonusSymbolProbability;
import com.assessment.model.probabilities.StandardProbability;
import com.assessment.model.probabilities.StandardSymbolProbability;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("C", "config", true, "config file at json format");
        options.addOption("B", "betting-amount", true, "the amount money you bet");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            String configFile = commandLine.getOptionValue("config");
            int bettingAmount = Integer.parseInt(commandLine.getOptionValue("betting-amount"));

            // Read input data from json file to object
            InputConfig inputConfig = readConfigData(configFile);

            // Initialize matrix
            String[][] matrix = new String[inputConfig.getRows()][inputConfig.getColumns()];

            // Generate standard symbol
            generateStandardSymbols(inputConfig, matrix);

            // Check winning combination
            Map<String, List<String>> appliedWinningCombinations = checkWinningCombination(matrix);

            // Generate bonus symbol
            String appliedBonusSymbol = null;
            if (!appliedWinningCombinations.isEmpty()) {
                appliedBonusSymbol = generateBonusSymbol(inputConfig);
            }

            // Calculate reward
            int reward = 0;
            if (!appliedWinningCombinations.isEmpty()) {
                reward = calculateReward(inputConfig, appliedWinningCombinations, bettingAmount);
                switch (appliedBonusSymbol) {
                    case "10x" -> reward *= 10;
                    case "5x" -> reward *= 5;
                    case "+1000" -> reward += 1000;
                    case "+500" -> reward += 500;
                }
            }

            // Print output
            Output output = new Output();
            output.setMatrix(matrix);
            output.setAppliedWinningCombinations(appliedWinningCombinations);
            output.setAppliedBonusSymbol(appliedBonusSymbol);
            output.setReward(reward);
            System.out.println(new ObjectMapper().writeValueAsString(output));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private static InputConfig readConfigData(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return new ObjectMapper().readValue(sb.toString(), InputConfig.class);
    }

    private static int generateRandomNumber(int max) {
        return (int) ((Math.random() * (max - 1)) + 1);
    }

    private static void generateStandardSymbols(InputConfig inputConfig, String[][] matrix) {
        StandardProbability[] standardProbabilities = inputConfig.getProbabilities().getStandardProbabilities();
        for (StandardProbability probability : standardProbabilities) {
            StandardSymbolProbability standardSymbolProbability = probability.getSymbols();
            int aProb = standardSymbolProbability.getA();
            int bProb = standardSymbolProbability.getB();
            int cProb = standardSymbolProbability.getC();
            int dProb = standardSymbolProbability.getD();
            int eProb = standardSymbolProbability.getE();
            int fProb = standardSymbolProbability.getF();
            int total = aProb + bProb + cProb + dProb + eProb + fProb;
            int randomNumber = generateRandomNumber(total);
            int row = probability.getRow();
            int col = probability.getColumn();
            if (randomNumber <= aProb) {
                matrix[row][col] = "A";
            } else if (randomNumber <= aProb + bProb) {
                matrix[row][col] = "B";
            } else if (randomNumber <= aProb + bProb + cProb) {
                matrix[row][col] = "C";
            } else if (randomNumber <= aProb + bProb + cProb + dProb) {
                matrix[row][col] = "D";
            } else if (randomNumber <= aProb + bProb + cProb + dProb + eProb) {
                matrix[row][col] = "E";
            } else {
                matrix[row][col] = "F";
            }
        }
    }

    private static String generateBonusSymbol(InputConfig inputConfig) {
        BonusProbability bonusProbability = inputConfig.getProbabilities().getBonusProbability();
        BonusSymbolProbability bonusSymbolProbability = bonusProbability.getSymbols();
        int x10 = bonusSymbolProbability.getX10();
        int x5 = bonusSymbolProbability.getX5();
        int plus1000 = bonusSymbolProbability.getPlus1000();
        int plus500 = bonusSymbolProbability.getPlus500();
        int miss = bonusSymbolProbability.getMiss();
        int total = x10 + x5 + plus500 + plus1000 + miss;
        int randomNumber = generateRandomNumber(total);
        if (randomNumber <= x10) {
            return "10x";
        } else if (randomNumber <= x10 + x5) {
            return "5x";
        } else if (randomNumber <= x10 + x5 + plus1000) {
            return "+1000";
        } else if (randomNumber <= x10 + x5 + plus1000 + plus500) {
            return "+500";
        }
        return "MISS";
    }

    private static Map<String, List<String>> checkWinningCombination(String[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        Map<String, Integer> map = new HashMap<>();
        for (String[] row : matrix) {
            for (String cell : row) {
                map.put(cell, map.getOrDefault(cell, 0) + 1);
            }
        }

        Map<String, List<String>> winCombinations = new HashMap<>();
        // check same symbols
        String[] standardSymbols = {"A", "B", "C", "D", "E", "F"};
        for (String symbol : standardSymbols) {
            if (map.containsKey(symbol)) {
                int same = map.get(symbol);
                List<String> combination = new ArrayList<>();
                switch (same) {
                    case 3 -> combination.add("same_symbol_3_times");
                    case 4 -> combination.add("same_symbol_4_times");
                    case 5 -> combination.add("same_symbol_5_times");
                    case 6 -> combination.add("same_symbol_6_times");
                    case 7 -> combination.add("same_symbol_7_times");
                    case 8 -> combination.add("same_symbol_8_times");
                    case 9 -> combination.add("same_symbol_9_times");
                }

                if (!combination.isEmpty()) {
                    winCombinations.put(symbol, combination);
                }
            }
        }

        // check same symbols horizontally
        for (String[] row : matrix) {
            String symbol = row[0];
            boolean isSame = true;
            for (int i = 1; i < row.length; i++) {
                if (!row[i].equals(symbol)) {
                    isSame = false;
                    break;
                }
            }
            if (isSame) {
                if (winCombinations.containsKey(symbol)) {
                    winCombinations.get(symbol).add("same_symbols_horizontally");
                } else {
                    winCombinations.put(symbol, List.of("same_symbols_horizontally"));
                }
            }
        }

        // check same symbols vertically
        for (int c = 0; c < columns; c++) {
            String symbol = matrix[0][c];
            boolean isSame = true;
            for (int r = 1; r < rows; r++) {
                if (!matrix[r][c].equals(symbol)) {
                    isSame = false;
                    break;
                }
            }
            if (isSame) {
                if (winCombinations.containsKey(symbol)) {
                    winCombinations.get(symbol).add("same_symbols_vertically");
                } else {
                    winCombinations.put(symbol, List.of("same_symbols_vertically"));
                }
            }
        }

        // check same symbols diagonally left to right
        String symbol = matrix[0][0];
        boolean isSame = true;
        for (int r = 1, c = 1; r < rows && c < columns; r++, c++) {
            if (!matrix[r][c].equals(symbol)) {
                isSame = false;
                break;
            }
        }
        if (isSame) {
            if (winCombinations.containsKey(symbol)) {
                winCombinations.get(symbol).add("same_symbols_diagonally_left_to_right");
            } else {
                winCombinations.put(symbol, List.of("same_symbols_diagonally_left_to_right"));
            }
        }

        // check same symbols diagonally right to left
        symbol = matrix[0][columns - 1];
        isSame = true;
        for (int r = 1, c = columns - 2; r < rows && c >= 0; r++, c--) {
            if (!symbol.equals(matrix[r][c])) {
                isSame = false;
                break;
            }
        }
        if (isSame) {
            if (winCombinations.containsKey(symbol)) {
                winCombinations.get(symbol).add("same_symbols_diagonally_left_to_right");
            } else {
                winCombinations.put(symbol, List.of("same_symbols_diagonally_left_to_right"));
            }
        }

        return winCombinations;
    }

    private static int calculateReward(InputConfig inputConfig, Map<String, List<String>> winningCombinations, int amount) {
        for (String symbol : winningCombinations.keySet()) {
            switch (symbol) {
                case "A" -> amount *= inputConfig.getSymbols().getASymbol().getRewardMultiplier();
                case "B" -> amount *= inputConfig.getSymbols().getBSymbol().getRewardMultiplier();
                case "C" -> amount *= inputConfig.getSymbols().getCSymbol().getRewardMultiplier();
                case "D" -> amount *= inputConfig.getSymbols().getDSymbol().getRewardMultiplier();
                case "E" -> amount *= inputConfig.getSymbols().getESymbol().getRewardMultiplier();
                case "F" -> amount *= inputConfig.getSymbols().getFSymbol().getRewardMultiplier();
            }

            for (String combination : winningCombinations.get(symbol)) {
                switch (combination) {
                    case "same_symbol_3_times" -> amount *= inputConfig.getWinCombinations().getSameSymbol3Times().getRewardMultiplier();
                    case "same_symbol_4_times" -> amount *= inputConfig.getWinCombinations().getSameSymbol4Times().getRewardMultiplier();
                    case "same_symbol_5_times" -> amount *= inputConfig.getWinCombinations().getSameSymbol5Times().getRewardMultiplier();
                    case "same_symbol_6_times" -> amount *= inputConfig.getWinCombinations().getSameSymbol6Times().getRewardMultiplier();
                    case "same_symbol_7_times" -> amount *= inputConfig.getWinCombinations().getSameSymbol7Times().getRewardMultiplier();
                    case "same_symbol_8_times" -> amount *= inputConfig.getWinCombinations().getSameSymbol8Times().getRewardMultiplier();
                    case "same_symbol_9_times" -> amount *= inputConfig.getWinCombinations().getSameSymbol9Times().getRewardMultiplier();
                    case "same_symbols_horizontally" -> amount *= inputConfig.getWinCombinations().getSameSymbolsHorizontally().getRewardMultiplier();
                    case "same_symbols_vertically" -> amount *= inputConfig.getWinCombinations().getSameSymbolsVertically().getRewardMultiplier();
                    case "same_symbols_diagonally_left_to_right" -> amount *= inputConfig.getWinCombinations().getSameSymbolsDiagonallyLeftToRight().getRewardMultiplier();
                    case "same_symbols_diagonally_right_to_left" -> amount *= inputConfig.getWinCombinations().getSameSymbolsDiagonallyRightToLeft().getRewardMultiplier();
                }
            }
        }
        return amount;
    }
}
