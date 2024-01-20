package org.sber.task3;

public class RockPaperScissorsPlayer implements PlayableRockPaperScissors {
    @Override
    public RockPaperScissorsEnum play() {

        var options = RockPaperScissorsEnum.values();

        int optionNumber = getRandomNumber(0, options.length - 1);

        return options[optionNumber];
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
