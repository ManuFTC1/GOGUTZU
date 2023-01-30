package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@TeleOp(name="Robot: Teleop POV", group="Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            runtime.startTime();

            if(gamepad1.a) {
                runtime.startTime();
                while (gamepad1.a) {
                    robot.setWheelsPower(-1);
                }
                try {
                    FileWriter myWriter = new FileWriter("Control Hub v1.0\\Internal shared storage\\Download\\inputRobot.txt");
                    myWriter.write('a' + " " + runtime + '\n');
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                robot.setWheelsPower(0);
                telemetry.addLine("a");
                continue;
            }
            if(gamepad1.y) {
                runtime.startTime();
                while(gamepad1.y) {
                    robot.setWheelsPower(1);
                }
                try {
                    FileWriter myWriter = new FileWriter("C:\\Users\\Claudiu\\Downloads\\FtcRobotController-8.1.1\\TeamCode\\src\\main\\java\\org\\firstinspires\\ftc\\teamcode\\inputRobot.txt");
                    myWriter.write('y' + " " + runtime + '\n');
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                robot.setWheelsPower(0);
                telemetry.addLine("y");
                continue;
            }

            if(gamepad1.x) {
                runtime.startTime();
                while (gamepad1.x) {
                    robot.setWheelsSpin(-1);
                }
                try {
                    FileWriter myWriter = new FileWriter("C:\\Users\\Claudiu\\Downloads\\FtcRobotController-8.1.1\\TeamCode\\src\\main\\java\\org\\firstinspires\\ftc\\teamcode\\inputRobot.txt");
                    myWriter.write('x' + " " + runtime + '\n');
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                robot.setWheelsSpin(0);
                telemetry.addLine("x");
                continue;
            }

            if(gamepad1.b) {
                runtime.startTime();
                while (gamepad1.b) {
                    robot.setWheelsSpin(1);
                }
                try {
                    FileWriter myWriter = new FileWriter("C:\\Users\\Claudiu\\Downloads\\FtcRobotController-8.1.1\\TeamCode\\src\\main\\java\\org\\firstinspires\\ftc\\teamcode\\inputRobot.txt");
                    myWriter.write('b' + " " + runtime + '\n');
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                robot.setWheelsSpin(0);
                telemetry.addLine("b");
                continue;
            }
            telemetry.update();
        }
        stop();
    }
}
/*
    double fata = 0;
    double spate = 0;
    double dreapta = 0;
    double stanga = 0;
    double timp = 0;

    waitForStart();

        while (opModeIsActive()) {
                runtime.startTime();

                if(gamepad1.y) {
                runtime.startTime();
                while(gamepad1.y) {
                robot.setWheelsPower(1);
                }
                try {
                FileWriter myWriter = new FileWriter("inputRobot.txt");
                myWriter.write('y' + " " + runtime + '\n');
                myWriter.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
                robot.setWheelsPower(1);
                return;
                }

                if(gamepad1.a) {
                runtime.startTime();
                while (gamepad1.b) {
                robot.setWheelsPower(-1);
                }
                try {
                FileWriter myWriter = new FileWriter("inputRobot.txt");
                myWriter.write('a' + " " + runtime + '\n');
                myWriter.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
                robot.setWheelsPower(-1);
                return;
                }

                if(gamepad1.x) {
                runtime.startTime();
                while (gamepad1.x) {

                }
                try {
                FileWriter myWriter = new FileWriter("inputRobot.txt");
                myWriter.write('x' + " " + runtime + '\n');
                myWriter.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
                robot.setWheelsSpin(-1);
                return;
                }

                if(gamepad1.b) {
                runtime.startTime();
                while (gamepad1.b) {


                }
                try {
                FileWriter myWriter = new FileWriter("inputRobot.txt");
                myWriter.write('b' + " " + runtime + '\n');
                myWriter.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
                robot.setWheelsSpin(1);
                return;
                }
                }
                stop();
                */