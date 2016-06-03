package edu.kit.robocup.recorder;

import edu.kit.robocup.constant.PitchSide;
import edu.kit.robocup.game.PlayMode;
import edu.kit.robocup.game.PlayerAction;
import edu.kit.robocup.game.controller.PlayerController;
import edu.kit.robocup.game.state.State;
import edu.kit.robocup.interf.game.IAction;
import edu.kit.robocup.game.controller.IPlayerController;

import java.io.*;
import java.util.List;
import java.util.Map;

import edu.kit.robocup.interf.mdp.IPolicy;
import edu.kit.robocup.interf.mdp.IState;
import org.apache.log4j.Logger;

import static cern.clhep.Units.s;

public class GameRecorder implements IPolicy {
    public static final String fileEnding = ".gl";
    static Logger logger = Logger.getLogger(GameRecorder.class.getName());
    private IPolicy policy;
    FileOutputStream fos;
    ObjectOutputStream oos;

    private File file;
    public GameRecorder(String fileName, IPolicy policy) {
        this.file = new File(fileName + fileEnding);
        this.policy = policy;
        try {
            this.fos = new FileOutputStream(this.file);
            this.oos = new ObjectOutputStream(this.fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void record(Serializable stateActionOrKey) {
        try {
            logger.info("recording: " + stateActionOrKey);
            this.oos.writeObject(stateActionOrKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void endRecording() {
        try {
            this.oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Map<IPlayerController, IAction> apply(IState state, List<? extends IPlayerController> playerControllers, PitchSide pitchSide) {

        //((State)state).clearPlayerStates();
        logger.info(state);

        record(state);
        Map<IPlayerController, IAction> actions = policy.apply(state, playerControllers, pitchSide);
        //record(actions);
        for (Map.Entry<IPlayerController, IAction> entry : actions.entrySet())
        {
            IPlayerController pc = entry.getKey();
            IAction action = entry.getValue();
            PlayerAction playerAction = new PlayerAction(pc.getNumber(), pc.getTeam().getPitchSide().toString(), action);
            //record(playerAction);

            //logger.info("recording: Player " + entry.getKey().getNumber() + " " + entry.getValue());
        }
        return actions;
    }
}
