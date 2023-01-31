package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotHardware {

    private ElapsedTime runtime = new ElapsedTime();

    public DcMotor motorStanga  = null;
    public DcMotor motorDreapta = null;

    public DcMotor bratRobot = null;
    public Servo manaDreapta = null;
    public Servo manaStanga  = null;

    com.qualcomm.robotcore.hardware.HardwareMap hwMap = null;

    public void init(com.qualcomm.robotcore.hardware.HardwareMap ahwMap) {

        hwMap = ahwMap;

        //Roti Robot
        motorStanga = hwMap.get(DcMotor.class, "LeftDrive");
        motorDreapta = hwMap.get(DcMotor.class, "RightDrive");

        motorStanga.setDirection(DcMotor.Direction.FORWARD);
        motorDreapta.setDirection(DcMotor.Direction.REVERSE);

        setWheelsPower(0);

        motorStanga.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorDreapta.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Brat Robot
        bratRobot = hwMap.get(DcMotor.class, "Arm");
        bratRobot.setDirection(DcMotor.Direction.FORWARD);
        bratRobot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bratRobot.setPower(0);

        //Maini Robot
        manaStanga = hwMap.get(Servo.class, "manaStanga");
        manaDreapta = hwMap.get(Servo.class, "manaDreapta");

        manaStanga.setDirection(Servo.Direction.FORWARD);
        manaDreapta.setDirection(Servo.Direction.REVERSE);

        setServoPosition(0.25);

        while (runtime.seconds() < 0.65) {//apesi init, bratu sus, se strang mainile, bratu jos
            bratRobot.setPower(-0.5);
        }

        while (runtime.seconds() < 0.7) {
            bratRobot.setPower(0.7);
        }

        bratRobot.setPower(0);
    }

    public void setWheelsPower(double allPower) {
        motorStanga.setPower(allPower);
        motorDreapta.setPower(allPower);
    }

    public void setServoPosition(double allPosition) {
        manaStanga.setPosition(allPosition);
        manaDreapta.setPosition(allPosition);
    }

}