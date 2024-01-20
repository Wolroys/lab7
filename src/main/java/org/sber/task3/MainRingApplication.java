package org.sber.task3;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class MainRingApplication {
    public static void main(String[] args) {
        Map<String, Class<?>> pluginsMap = getPlugins();
        Queue<Map.Entry<String, Class<?>>> queue = new LinkedList<>(pluginsMap.entrySet());

        while (queue.size() > 1) {
            playMatch(queue);
        }

        if (!queue.isEmpty()) {
            System.out.println("===============================");
            System.out.println("CONGRATULATIONS " + queue.poll().getKey() + "!!!!!!!!!");
            System.out.println("===============================");
        }
    }

    private static void playMatch(Queue<Map.Entry<String, Class<?>>> queue) {
        Map.Entry<String, Class<?>> entry1 = queue.poll();
        Map.Entry<String, Class<?>> entry2 = queue.poll();

        int score1 = 0, score2 = 0;

        for (int round = 1; round <= 3; round++) {
            int result = playRound(entry1, entry2, round);
            if (result == 1) {
                score1++;
            } else if (result == -1) {
                score2++;
            }
        }

        decideWinner(score1, score2, entry1, entry2, queue);
    }

    private static int playRound(Map.Entry<String, Class<?>> entry1, Map.Entry<String, Class<?>> entry2, int round) {
        try {
            PlayableRockPaperScissors player1 = (PlayableRockPaperScissors) entry1.getValue().newInstance();
            PlayableRockPaperScissors player2 = (PlayableRockPaperScissors) entry2.getValue().newInstance();

            RockPaperScissorsEnum move1 = player1.play();
            RockPaperScissorsEnum move2 = player2.play();
            System.out.println("Round " + round + ":");
            System.out.println("Player 1 (" + entry1.getKey() + ") plays " + move1);
            System.out.println("Player 2 (" + entry2.getKey() + ") plays " + move2);

            return compareMoves(move1, move2);

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static void decideWinner(int score1, int score2, Map.Entry<String, Class<?>> entry1,
                                     Map.Entry<String, Class<?>> entry2, Queue<Map.Entry<String, Class<?>>> queue) {
        if (score1 > score2) {
            System.out.println(entry1.getKey() + " wins the match");
            queue.add(entry1);
        } else if (score2 > score1) {
            System.out.println(entry2.getKey() + " wins the match");
            queue.add(entry2);
        } else {
            queue.add(entry1);
            queue.add(entry2);
        }
    }

    private static int compareMoves(RockPaperScissorsEnum choice1, RockPaperScissorsEnum choice2) {
        if (choice1 == choice2) {
            return 0;
        }
        return switch (choice1) {
            case ROCK -> (choice2 == RockPaperScissorsEnum.SCISSORS) ? 1 : -1;
            case PAPER -> (choice2 == RockPaperScissorsEnum.ROCK) ? 1 : -1;
            case SCISSORS -> (choice2 == RockPaperScissorsEnum.PAPER) ? 1 : -1;
        };
    }

    private static Map<String, Class<?>> getPlugins() {
        File[] jars = getPluginFiles();
        Map<String, Class<?>> pluginClasses = new HashMap<>();
        for (File jar : jars) {
            try {
                URL jarURL = jar.toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
                String fileName = jar.getName();
                Class<?> clazz =
                        classLoader.loadClass("org.sber.task3.RockPaperScissorsPlayer");
                pluginClasses.put(fileName, clazz);
                classLoader.close();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
        return pluginClasses;
    }

    private static File[] getPluginFiles() {
        File pluginDir = new File("C:\\Users\\averg\\IdeaProjects\\lab7\\plugins");

        return pluginDir.listFiles(file ->
                file.isFile() && file.getName().endsWith(".jar"));
    }
}
