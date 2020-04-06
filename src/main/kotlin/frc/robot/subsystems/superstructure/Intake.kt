package frc.robot.subsystems.superstructure

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.PWMSparkMax
import edu.wpi.first.wpilibj.Solenoid
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.wrappers.FalconDoubleSolenoid

object Intake: FalconSubsystem() {

    val solenoid = DoubleSolenoid(8, 0, 1)
    val motor = PWMSparkMax(0)

}