package org.firstinspires.ftc.LTASrob.OpModes;

import org.firstinspires.ftc.LTASrob.RobotHardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;


import java.io.File;

@TeleOp(name="InregistrareStanga", group="Robot")
public class WriteFIleStanga extends LinearOpMode {
    @Override
    public void runOpMode() {
        ElapsedTime runtimeRoti = new ElapsedTime();
        ElapsedTime runtimeBrat = new ElapsedTime();
        
        RobotHardware robot = new RobotHardware();

        robot.init(hardwareMap);
        robot.brakeWheels(true);

        File filaRoti = new File("/storage/emulated/0/FIRST/InputRoti1.txt");
        File filaBrat = new File("/storage/emulated/0/FIRST/InputBrat1.txt");

        File filaRotiStanga = new File("/storage/emulated/0/FIRST/InputRotiStanga.txt");
        File filaBratStanga = new File("/storage/emulated/0/FIRST/InputBratStanga.txt");

        File filaRotiMijloc = new File("/storage/emulated/0/FIRST/InputRotiMijloc.txt");
        File filaBratMijloc = new File("/storage/emulated/0/FIRST/InputBratMijloc.txt");

        File filaRotiDreapta = new File("/storage/emulated/0/FIRST/InputRotiDreapta.txt");
        File filaBratDreapta = new File("/storage/emulated/0/FIRST/InputBratDreapta.txt");

        String rotiAnterior = "0";
        String bratAnterior = "0";


        String rotiFinal = "";
        String bratFinal = "";
        
        waitForStart();
        runtimeRoti.startTime();
        runtimeBrat.reset();

        while (opModeIsActive()) {

            telemetry.addData("fila brat: ", filaBrat.getPath());
            telemetry.addData("fila roti: ", filaRoti.getPath());
            telemetry.update();

            switch (directieRoti()) {
                case "y":
                    if (rotiAnterior != "y") {
                        rotiFinal += rotiAnterior + " " + runtimeRoti.milliseconds() + " ";
                        runtimeRoti.reset();
                    }
                    robot.setWheelsPower(1);
                    rotiAnterior = "y";
                    break;

                case "a":
                    if (rotiAnterior != "a") {
                        rotiFinal += rotiAnterior + " " + runtimeRoti.milliseconds() + " ";
                        runtimeRoti.reset();
                    }
                    robot.setWheelsPower(-1);
                    rotiAnterior = "a";
                    break;

                case "x":
                    if (rotiAnterior != "x") {
                        rotiFinal += rotiAnterior + " " + runtimeRoti.milliseconds() + " ";
                        runtimeRoti.reset();
                    }
                    robot.motorDreapta.setPower(1);
                    robot.motorStanga.setPower(-1);
                    rotiAnterior = "x";
                    break;

                case "b":
                    if (rotiAnterior != "b") {
                        rotiFinal += rotiAnterior + " " + runtimeRoti.milliseconds() + " ";
                        runtimeRoti.reset();
                    }
                    robot.motorDreapta.setPower(-1);
                    robot.motorStanga.setPower(1);
                    rotiAnterior = "b";
                    break;

                default:
                    if (rotiAnterior != "0") {
                        rotiFinal += rotiAnterior + " " + runtimeRoti.milliseconds() + " ";
                        runtimeRoti.reset();
                    }
                    rotiAnterior = "0";
                    robot.setWheelsPower(0);
            }

            switch (manevraBrat()) {
                case "y":
                    if (bratAnterior != "y") {
                        bratFinal += bratAnterior + " " + runtimeBrat.milliseconds() + " ";
                        runtimeBrat.reset();
                    }
                    robot.bratRobot.setPower(-1);
                    bratAnterior = "y";
                    break;

                case "a":
                    if (bratAnterior != "a") {
                        bratFinal += bratAnterior + " " + runtimeBrat.milliseconds() + " ";
                        runtimeBrat.reset();
                    }
                    robot.bratRobot.setPower(1);
                    bratAnterior = "a";
                    break;

                case "x":
                    if (bratAnterior != "x") {
                        bratFinal += bratAnterior + " " + runtimeBrat.milliseconds() + " ";
                        runtimeBrat.reset();
                    }
                    robot.setServoPosition(0.74);
                    bratAnterior = "x";
                    break;

                case "b":
                    if (bratAnterior != "b") {
                        bratFinal += bratAnterior + " " + runtimeBrat.milliseconds() + " ";
                        runtimeBrat.reset();
                    }
                    robot.setServoPosition(1);
                    bratAnterior = "b";
                    break;

                default:
                    if (bratAnterior != "0") {
                        bratFinal += bratAnterior + " " + runtimeBrat.milliseconds() + " ";
                        runtimeBrat.reset();
                    }
                    bratAnterior = "0";
                    robot.bratRobot.setPower(0);
            }
            if(gamepad2.left_bumper || gamepad2.right_bumper) {
                break;
            }
            if(gamepad2.right_trigger == 1){
                filaBrat.delete();
                filaRoti.delete();
                stop();
                break;
            }
        }
        rotiFinal += bratAnterior + " " + runtimeRoti.milliseconds() + "0 0";
        bratFinal += rotiAnterior + " " + runtimeBrat.milliseconds() + "0 0";
        
        ReadWriteFile.writeFile(filaRoti, rotiFinal);
        ReadWriteFile.writeFile(filaBrat, bratFinal);

        stop();
    }

    public String directieRoti() {
        if (gamepad1.y)
            return "y";

        if (gamepad1.a)
            return "a";

        if (gamepad1.x)
            return "x";

        if (gamepad1.b)
            return "b";

        return "0";
    }

    public String manevraBrat() {
        if (gamepad2.y)
            return "y";

        if (gamepad2.a)
            return "a";

        if (gamepad2.x)
            return "x";

        if (gamepad2.b)
            return "b";

        return "0";
    }
}