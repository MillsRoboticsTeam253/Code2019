package frc.robot.Drivetrain;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Drivetrain.VisionPID;

public class VisionDrive extends Command {

    private double left, right;
    
    private double aim_kP = 0.01;
    private double aim_kI = 0;
    private double aim_kD = 0;

    private double dist_kP = 0;
    private double dist_kI = 0;
    private double dist_kD = 0;

    private double kFriction = 0.05;

    private VisionPID aim = new VisionPID(aim_kP, aim_kI, aim_kD, 0.02);
    private VisionPID distance = new VisionPID(dist_kP, dist_kI, dist_kD, 0.02);

    
    public VisionDrive() {
        requires(Robot.drivetrain);

    }

    protected void execute() {

        double turn = Robot.oi.getTurnValue();

        double heading_error;
        double distance_error;

        if (!Robot.oi.getTargetValid()) {

            SmartDashboard.putBoolean("Has Target", false);

            heading_error = Robot.oi.getLastValidXOffset();
            distance_error = 0;
            
        } else {

            SmartDashboard.putBoolean("Has Target", true);

            heading_error = Robot.oi.getxOffset();
            distance_error = Robot.oi.getyOffset();

        }

        SmartDashboard.putNumber("distance_err", distance_error);

        SmartDashboard.putNumber("heading_error", heading_error);

        double steering_adjust = aim.calculate(heading_error);
        double distance_adjust = distance.calculate(distance_error);

        left = distance_adjust - steering_adjust;
        right = distance_adjust + steering_adjust;

        left += left > 0 ? kFriction : -kFriction;
        right += right > 0 ? kFriction : -kFriction;

        Drivetrain.drive(Robot.oi.getThrottleValue() + left + turn,
                Robot.oi.getThrottleValue() + right - turn);

        SmartDashboard.putNumber("left", left);
        SmartDashboard.putNumber("right", right);

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Drivetrain.drive(0, 0);
    }

}