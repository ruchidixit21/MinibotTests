package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team670.robot.Robot;
import frc.team670.robot.utils.Logger;

public class BalancedDrive extends Command {
	
	private double speedL, speedR, seconds;
	
	public BalancedDrive(double seconds, double speed) {
		this.speedL = speed;
		this.speedR = speed;
		this.seconds = seconds;
		requires(Robot.driveBase);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		setTimeout(seconds);
		Logger.consoleLog("LeftSpeed: %s Right Speed: %s Seconds: %s", 
				speedL, speedR, seconds);
	}	

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() { 
		Logger.consoleLog();
		Robot.driveBase.tankDrive(speedL, speedR, false);
		correct();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveBase.stop();
		Logger.consoleLog("LeftSpeed: %s Right Speed: %s Seconds: %s", 
				speedL, speedR, seconds);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}	
	
	// Checks that the wheels are driving at the same speed, corrects the speed
	// so that the left/right are equal
	public void correct() {
		double currentTicksL = Robot.driveBase.getLeftEncoder().getTicks();
		double currentTicksR = Robot.driveBase.getRightEncoder().getTicks();
		
		if (Math.abs(currentTicksL - currentTicksR) < 5)
			return;
		
		else if (currentTicksL > currentTicksR)
			speedL -= 0.01;
		
		else if (currentTicksL < currentTicksR)
			speedR -= 0.01;

	}

}
