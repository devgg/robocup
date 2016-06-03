package edu.kit.robocup.recorder;

import com.github.robocup_atan.atan.model.enums.PlayMode;
import edu.kit.robocup.Util;
import edu.kit.robocup.constant.PitchSide;
import edu.kit.robocup.game.controller.Team;
import edu.kit.robocup.game.controller.Trainer;
import edu.kit.robocup.game.state.Ball;
import edu.kit.robocup.game.state.PlayerState;
import edu.kit.robocup.mdp.policy.RandomPolicy;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

public class Recorder {

    public static void main(String[] args) throws IOException {
        initEnvironment();

        /*
        * Connect desired teams here
        */
        Trainer trainer = new Trainer("Trainer");
        trainer.connect();


        GameRecorder recorder = new GameRecorder("test", new RandomPolicy());

        Team team1 = new Team(PitchSide.WEST, 1, recorder);
        team1.connectAll();

        Team team2 = new Team(PitchSide.EAST, 2, new RandomPolicy());
        team2.connectAll();

        trainer.movePlayer(new PlayerState(PitchSide.WEST, 1, 20, 20));
        trainer.movePlayer(new PlayerState(PitchSide.EAST, 1, 20, 20));

        trainer.moveBall(new Ball(3, 3));


        team1.getCoach().eye(true); // enables constant visual updates for trainer/coach
        trainer.changePlayMode(PlayMode.KICK_OFF_L);

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                recorder.endRecording();
            }
        });
    }

    private static void initEnvironment() {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        Util.startServer();
        Util.startMonitor();
    }

}
