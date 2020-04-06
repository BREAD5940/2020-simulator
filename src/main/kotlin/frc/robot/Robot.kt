package frc.robot

import edu.wpi.first.wpilibj.DoubleSolenoid
import frc.robot.subsystems.superstructure.Hood
import frc.robot.subsystems.superstructure.Intake
import org.ghrobotics.lib.wrappers.FalconTimedRobot

object Robot : FalconTimedRobot() {

    override fun robotInit() {
        +Intake
        +Hood
    }

    override fun teleopInit() {
        Intake.solenoid.set(DoubleSolenoid.Value.kForward)
        Intake.motor.set(1.0)
    }

    override fun disabledInit() {
        Intake.solenoid.set(DoubleSolenoid.Value.kReverse)
        Intake.motor.set(0.0)
    }

}

fun main() {
    Robot.start()
}
