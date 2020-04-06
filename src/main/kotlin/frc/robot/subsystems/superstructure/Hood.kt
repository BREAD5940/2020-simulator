package frc.robot.subsystems.superstructure

import edu.wpi.first.wpilibj.AnalogInput
import edu.wpi.first.wpilibj.PWMSparkMax
import edu.wpi.first.wpilibj.RobotBase
import org.ghrobotics.lib.commands.FalconSubsystem

object Hood : FalconSubsystem() {

    val encoder = AnalogInput(0)
    val motor = PWMSparkMax(1)

    override fun periodic() {
        motor.set(encoder.averageVoltage - 2.5)

        if(RobotBase.isSimulation()) {
            encoder.simObject.voltage += 0.1
            encoder.simObject.voltage %= 5
        }
    }
}