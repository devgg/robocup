package edu.kit.robocup.interf.mdp;

import edu.kit.robocup.interf.game.IPlayer;
import edu.kit.robocup.game.state.Ball;
import edu.kit.robocup.interf.game.IPlayerState;

import java.io.Serializable;
import java.util.List;

public interface IState extends Serializable {
    Ball getBall();
    List<IPlayerState> getPlayers(final String teamName);
    IPlayerState getPlayerState(IPlayer player);
}