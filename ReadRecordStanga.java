package org.firstinspires.ftc.LTASrob.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.LTASrob.RobotHardware;

import java.io.File;

@Autonomous(name="Citit Miscari Stanga", group="Robot")
public class ReadRecordStanga extends LinearOpMode {

    File filaRoti = new File("/storage/emulated/0/FIRST/InputRoti1.txt");
    File filaBrat = new File("/storage/emulated/0/FIRST/InputBrat1.txt");

    String rotiTotal = ReadWriteFile.readFile(filaRoti);
    String bratTotal = ReadWriteFile.readFile(filaBrat);

    @Override
    public void runOpMode() {

        ElapsedTime runtimeBrat = new ElapsedTime();
        ElapsedTime runtimeDirectie = new ElapsedTime();

        RobotHardware robot = new RobotHardware();

        robot.init(hardwareMap);
        robot.brakeWheels(true);

        double delayBrat = 0;
        double delayRoti = 0;

        String directieRoti = urmatorulCuvantRoti();
        String manevraBrat  = urmatorulCuvantBrat();

        double timpRoti = Double.parseDouble(urmatorulCuvantRoti());
        double timpBrat = Double.parseDouble(urmatorulCuvantBrat())  + 100;


        waitForStart();

        runtimeDirectie.startTime();
        runtimeDirectie.reset();

        runtimeBrat.startTime();
        runtimeBrat.reset();

        while (opModeIsActive()) {
            telemetry.addData("directie: ", directieRoti + "timp: " + timpRoti);
            telemetry.addData("brat: ", manevraBrat + "timp " + timpBrat);
            telemetry.update();


            switch (directieRoti) {
                case "y":
                    if (timpRoti + delayRoti > runtimeDirectie.milliseconds())
                        robot.setWheelsPower(1);
                    else {
                        directieRoti = urmatorulCuvantRoti();
                        timpRoti = Double.parseDouble(urmatorulCuvantRoti());
                        runtimeDirectie.reset();
                    }
                    break;
                case "a":
                    if (timpRoti + delayRoti > runtimeDirectie.milliseconds())
                        robot.setWheelsPower(-1);
                    else {
                        directieRoti = urmatorulCuvantRoti();
                        timpRoti = Double.parseDouble(urmatorulCuvantRoti());
                        runtimeDirectie.reset();
                    }
                    break;
                case "x":
                    if (timpRoti + delayRoti > runtimeDirectie.milliseconds()) {
                        robot.motorDreapta.setPower(1);
                        robot.motorStanga.setPower(-1);
                    } else {
                        directieRoti = urmatorulCuvantRoti();
                        timpRoti = Double.parseDouble(urmatorulCuvantRoti());
                        runtimeDirectie.reset();
                    }
                    break;
                case "b":
                    if (timpRoti + delayRoti > runtimeDirectie.milliseconds()) {
                        robot.motorDreapta.setPower(-1);
                        robot.motorStanga.setPower(1);
                    } else {
                        directieRoti = urmatorulCuvantRoti();
                        timpRoti = Double.parseDouble(urmatorulCuvantRoti());
                        runtimeDirectie.reset();
                    }
                    break;

                default:
                    if (timpRoti + delayRoti > runtimeDirectie.milliseconds()) {
                        robot.setWheelsPower(0);
                    } else {
                        directieRoti = urmatorulCuvantRoti();
                        timpRoti = Double.parseDouble(urmatorulCuvantRoti());
                        runtimeDirectie.reset();
                    }
            }

            switch (manevraBrat) {
                case "y":
                    if (timpBrat + delayBrat > runtimeBrat.milliseconds())
                        robot.bratRobot.setPower(-1);
                    else {
                        manevraBrat = urmatorulCuvantBrat();
                        timpBrat = Double.parseDouble(urmatorulCuvantBrat());
                        runtimeBrat.reset();
                    }
                    break;
                case "a":
                    if (timpBrat + delayBrat > runtimeBrat.milliseconds())
                        robot.bratRobot.setPower(1);
                    else {
                        manevraBrat = urmatorulCuvantBrat();
                        timpBrat = Double.parseDouble(urmatorulCuvantBrat());
                        runtimeBrat.reset();
                    }
                    break;
                case "x":
                    if (timpBrat + delayBrat > runtimeBrat.milliseconds())
                        robot.setServoPosition(0.74);
                    else {
                        manevraBrat = urmatorulCuvantBrat();
                        timpBrat = Double.parseDouble(urmatorulCuvantBrat());
                        runtimeBrat.reset();
                    }
                    break;
                case "b":
                    if (timpBrat + delayBrat > runtimeBrat.milliseconds())
                        robot.setServoPosition(1);
                    else {
                        manevraBrat = urmatorulCuvantBrat();
                        timpBrat = Double.parseDouble(urmatorulCuvantBrat());
                        runtimeBrat.reset();
                    }
                    break;

                default:
                    if (timpBrat + delayBrat > runtimeBrat.milliseconds())
                        robot.bratRobot.setPower(0);
                    else {
                        manevraBrat = urmatorulCuvantBrat();
                        timpBrat = Double.parseDouble(urmatorulCuvantBrat());
                        runtimeBrat.reset();
                    }
            }
        }
        stop();
    }

    public String urmatorulCuvantRoti() {
            String valoare = "";
            int i;

            for (i = 0; rotiTotal.charAt(i) != ' '; i++)
                valoare += rotiTotal.charAt(i);
            rotiTotal = rotiTotal.substring(i + 1);

            return valoare;
    }

    public String urmatorulCuvantBrat() {
            String valoare = "";
            int i;

            for (i = 0; bratTotal.charAt(i) != ' '; i++)
                valoare += bratTotal.charAt(i);
            bratTotal = bratTotal.substring(i + 1);

            return valoare;
    }
}
