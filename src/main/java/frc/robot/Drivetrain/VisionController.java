package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionController {

    private double kP, kI, kD, dt;
    private double last_error = 0;
    private double counter = 0;

    public VisionController(double kP, double kI, double kD, double dt) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.dt = dt;
        
    }

    public void configure(double initial_error) {
        this.last_error = initial_error;
    }

    public void updatePID(double kP, double kI, double kD){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double calculate(double error) {

        double ret;

        if (counter == 0) {
            ret = kP * error + kD * (error - error / 2) / dt;
        } else {
            ret = kP * error + kD * (error - last_error) / dt;
            this.last_error = error;

        }
        counter++;
        return ret;
    }
}