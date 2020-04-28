package frc.robot;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Units;

public class Robot extends TimedRobot {

    // Controllers
    XboxController controller;

    // Elevator stuff here
    Encoder elevatorEncoder;
    DigitalInput elevatorLimit;
    PWMSparkMax elevatorMotor;

    // Sim stuff
    SimDevice device = SimDevice.create("Elevator Encoder");
    private SimDouble posSim;
    private SimDouble velSim;

    @Override
    public void robotInit() {
        controller = new XboxController(0);

        elevatorEncoder = new Encoder(0, 1);
        elevatorEncoder.setDistancePerPulse(
                // (dist per rev) / (ppr) = dist / pulse
                // elevator drum with a 1in radius
                (2.0 * Math.PI * 1.0) / 8192.0
        );

        elevatorLimit = new DigitalInput(2);
        elevatorMotor = new PWMSparkMax(0);

        if(device != null) {
            this.posSim = device.createDouble("Position, m", true, 0.0);
            this.velSim = device.createDouble("Velocity, m/s", true, 10.0);
        }
    }

    // PUT CODE HERE!
    @Override
    public void teleopPeriodic() {
        elevatorMotor.set(1);
    }

    // This simulates the elevator
    @Override
    public void simulationPeriodic() {
        double elevatorVoltage;
        if(isEnabled()) elevatorVoltage = elevatorMotor.get() * RobotController.getBatteryVoltage();
                else elevatorVoltage = 0.0;

        // Characterization
        var elevatorKv = 12 / Units.inchesToMeters(40);
        var elevatorKa = 12 / Units.inchesToMeters(50);

        // We know V = kV * v + kA * a
        // I know a = -kv/ka v + 1/ka * V
        var a = -elevatorKv/elevatorKa * velSim.get()
                + 1 / elevatorKa * elevatorVoltage;

        System.out.println(String.format("Voltage %s, a %s", elevatorVoltage, a));

        var deltaPos = velSim.get() * m_period;
        posSim.set(posSim.get() + deltaPos);

        velSim.set(velSim.get() + a * m_period);
    }

    public static void main(String[] args) {
        RobotBase.startRobot(Robot::new);
    }
}
