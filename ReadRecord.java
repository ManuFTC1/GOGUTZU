package org.firstinspires.ftc.LTASrob.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.LTASrob.RobotHardware;
import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

import java.io.File;

@Autonomous(name="Citit Miscari", group="Robot")
@Disabled
public class ReadRecord extends LinearOpMode {

    @Override
    public void runOpMode() {

        ElapsedTime runtime = new ElapsedTime();
        RobotHardware robot = new RobotHardware();

        robot.init(hardwareMap);
        robot.brakeWheels(true);

        File filaRoti = new File("/storage/emulated/0/FIRST/InputRoti.txt");
        File filaBrat = new File("/storage/emulated/0/FIRST/InputBrat.txt");

        //bratu si rotile sunt in fisiere diferite
        //model: y 200 x 314 x 316

        double delayBrat = 0;
        double delayRoti = 0;

        String directieRoti = ReadWriteFile.readFile(filaRoti);
        String manevraBrat  = ReadWriteFile.readFile(filaBrat);

        double timpRoti = Integer.parseInt(ReadWriteFile.readFile(filaRoti));
        double timpBrat = Integer.parseInt(ReadWriteFile.readFile(filaRoti));

        waitForStart();

        runtime.startTime();
        runtime.reset();

        while (opModeIsActive()) {

            switch (directieRoti) {
                case "y":
                    if (timpRoti + delayRoti < runtime.milliseconds())
                        robot.setWheelsPower(1);
                    else {
                        robot.setWheelsPower(0);
                        directieRoti = ReadWriteFile.readFile(filaRoti);
                        timpRoti = Integer.parseInt(ReadWriteFile.readFile(filaRoti));
                    }
                    break;
                case "a":
                    if (timpRoti + delayRoti < runtime.milliseconds())
                        robot.setWheelsPower(-1);
                    else {
                        robot.setWheelsPower(0);
                        directieRoti = ReadWriteFile.readFile(filaRoti);
                        timpRoti = Integer.parseInt(ReadWriteFile.readFile(filaRoti));
                    }
                    break;
                case "x":
                    if (timpRoti + delayRoti < runtime.milliseconds()) {
                        robot.motorDreapta.setPower(1);
                        robot.motorStanga.setPower(-1);
                    } else {
                        robot.setWheelsPower(0);
                        directieRoti = ReadWriteFile.readFile(filaRoti);
                        timpRoti = Integer.parseInt(ReadWriteFile.readFile(filaRoti));
                    }
                    break;
                case "b":
                    if (timpRoti + delayRoti < runtime.milliseconds()) {
                        robot.motorDreapta.setPower(-1);
                        robot.motorStanga.setPower(1);
                    } else {
                        robot.setWheelsPower(0);
                        directieRoti = ReadWriteFile.readFile(filaRoti);
                        timpRoti = Integer.parseInt(ReadWriteFile.readFile(filaRoti));
                    }
                    break;

                default:
                    robot.setWheelsPower(0);
            }

            switch (manevraBrat) {
                case "y":
                    if (timpBrat + delayBrat < runtime.milliseconds())
                        robot.bratRobot.setPower(1);
                    else {
                        robot.bratRobot.setPower(0);
                        manevraBrat = ReadWriteFile.readFile(filaBrat);
                        timpBrat = Integer.parseInt(ReadWriteFile.readFile(filaBrat));
                    }
                    break;
                case "a":
                    if (timpBrat + delayBrat < runtime.milliseconds())
                        robot.bratRobot.setPower(-1);
                    else {
                        robot.bratRobot.setPower(0);
                        manevraBrat = ReadWriteFile.readFile(filaBrat);
                        timpBrat = Integer.parseInt(ReadWriteFile.readFile(filaBrat));
                    }
                    break;
                case "x":
                    if (timpBrat + delayBrat < runtime.milliseconds())
                        robot.setServoPosition(0.74);
                    else {
                        manevraBrat = ReadWriteFile.readFile(filaBrat);
                        timpBrat = Integer.parseInt(ReadWriteFile.readFile(filaBrat));
                    }
                    break;
                case "b":
                    if (timpBrat + delayBrat < runtime.milliseconds())
                        robot.setServoPosition(1);
                    else {
                        manevraBrat = ReadWriteFile.readFile(filaBrat);
                        timpBrat = Integer.parseInt(ReadWriteFile.readFile(filaBrat));
                    }
                    break;

                default:
                    robot.bratRobot.setPower(0);
                    timpBrat = Integer.parseInt(ReadWriteFile.readFile(filaBrat));
            }
        }
        stop();

    }
}
