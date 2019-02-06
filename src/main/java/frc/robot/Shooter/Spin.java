package frc.robot.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Spin extends Command {

    private double lspeed, hspeed;

    public Spin(double lspeed, double hspeed){
        requires(Robot.shooter);
        
        this.lspeed = lspeed;
        this.hspeed = hspeed;
    }

    protected void execute(){
        ShooterSubsystem.spin(lspeed, hspeed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end(){
        ShooterSubsystem.spin(0, 0);
    }

}