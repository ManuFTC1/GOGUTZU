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

        motorStanga.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorDreapta.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setWheelsPower(0);

        //Brat Robot
        bratRobot = hwMap.get(DcMotor.class, "Arm");
        bratRobot.setDirection(DcMotor.Direction.FORWARD);
        bratRobot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bratRobot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//blocheaza bratu

        bratRobot.setPower(0);

        //Maini Robot
        manaStanga = hwMap.get(Servo.class, "manaStanga");
        manaDreapta = hwMap.get(Servo.class, "manaDreapta");

        manaStanga.setDirection(Servo.Direction.FORWARD);
        manaDreapta.setDirection(Servo.Direction.REVERSE);

        manaStanga.getPosition();


        //daca bratu ii deschis, se inchide in init
        if(manaStanga.getPosition() > 0.25 || manaDreapta.getPosition() > 0.25){
            setServoPosition(0.25);

            while (runtime.seconds() < 0.65) {
                bratRobot.setPower(-0.5);
            }

            while (runtime.seconds() < 1.3) {
                bratRobot.setPower(0.2);
            }

            bratRobot.setPower(0);
        }
    }

    public void setWheelsPower(double allPower) {
        motorStanga.setPower(allPower);
        motorDreapta.setPower(allPower);
    }

    public void setServoPosition(double allPosition) {
        manaStanga.setPosition(allPosition);
        manaDreapta.setPosition(allPosition);
    }

    public void brakeWheels(boolean x) {
        if(x) {
            setWheelsPower(0);
            motorStanga.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorDreapta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else {
            motorStanga.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorDreapta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }
}