package edu.kit.robocup.game;

import edu.kit.robocup.constant.Constants;
import edu.kit.robocup.game.state.Ball;
import edu.kit.robocup.game.state.PlayerState;
import edu.kit.robocup.game.state.State;
import edu.kit.robocup.interf.game.IPlayerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dani on 27.05.2016.
 */
public class StateFactory {

    public StateFactory() {};

    public State getRandomState(int numberPlayers, String teamname) {
        Ball ball = new Ball(getRandomXPosition(), getRandomYPosition(), getRandomDouble(-100,100), getRandomDouble(-100,100));
        List<IPlayerState> p = new ArrayList<>();
        for (int i = 0; i < numberPlayers; i++) {
            p.add(new PlayerState(teamname, i, getRandomXPosition(), getRandomYPosition(), getRandomDouble(-100, 100), getRandomDouble(-100, 100), getRandomDouble(Constants.minmoment, Constants.maxmoment), getRandomDouble(Constants.minneckmoment, Constants.maxneckmoment)));
        }
        return new State(ball, p);
    }

    private double getRandomXPosition() {
        return getRandomDouble(-Constants.PITCH_LENGTH/2.0, Constants.PITCH_LENGTH/2.0);
    }

    private double getRandomYPosition() {
        return getRandomDouble(-Constants.PITCH_WIDTH/2.0, Constants.PITCH_WIDTH/2.0);
    }

    private double getRandomDouble(double min, double max) {
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();
        return randomValue;
    }

    public static void main(String[] args) {
        StateFactory s = new StateFactory();
        State state = s.getRandomState(3, "dummy");
        System.out.println(state.toString());
    }

}
