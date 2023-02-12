package org.firstinspires.ftc.LTASrob.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.LTASrob.RobotHardware;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Disabled
@TeleOp(name="Scris Miscari", group="Robot")
public class WriteFile extends LinearOpMode {
    @Override
    public void runOpMode() {
        ElapsedTime runtime = new ElapsedTime();
        RobotHardware robot = new RobotHardware();

        robot.init(hardwareMap);
        robot.brakeWheels(true);

        File filaRoti = new File("/storage/emulated/0/FIRST/InputRoti.txt");
        File filaBrat = new File("/storage/emulated/0/FIRST/InputBrat.txt");


        char rotiAnterior = 0;
        char bratAnterior = 0;

        waitForStart();
        runtime.startTime();
        runtime.reset();

        while (opModeIsActive()) {

            switch (directieRoti()) {
                case 'y':
                    if(rotiAnterior != '0') {
                        ReadWriteFile.writeFile(filaRoti, 'y' + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    robot.setWheelsPower(1);
                    rotiAnterior = 'y';
                    break;

                case 'a':
                    if(rotiAnterior != '0') {
                        ReadWriteFile.writeFile(filaRoti, 'a' + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    robot.setWheelsPower(-1);
                    rotiAnterior = 'a';
                    break;

                case 'x':
                    if(rotiAnterior != '0') {
                        ReadWriteFile.writeFile(filaRoti, 'x' + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    robot.motorDreapta.setPower(1);
                    robot.motorStanga.setPower(-1);
                    rotiAnterior = 'x';
                    break;

                case 'b':
                    if(rotiAnterior != '0') {
                        ReadWriteFile.writeFile(filaRoti, 'b' + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    robot.motorDreapta.setPower(-1);
                    robot.motorStanga.setPower(1);
                    rotiAnterior = 'b';
                    break;

                default:
                    if(rotiAnterior != '0') {
                        ReadWriteFile.writeFile(filaRoti, '0' + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    rotiAnterior = '0';
                    robot.setWheelsPower(0);
            }

            switch (manevraBrat()) {
                case 'y':
                    if(bratAnterior == '0') {
                        ReadWriteFile.writeFile(filaBrat, 'y' + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    robot.bratRobot.setPower(1);
                    bratAnterior = 'y';
                    break;

                case 'a':
                    if(bratAnterior == '0') {
                        ReadWriteFile.writeFile(filaBrat, 'a' + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    robot.bratRobot.setPower(-1);
                    bratAnterior = 'a';
                    break;

                case 'x':
                    if(bratAnterior == '0') {
                        ReadWriteFile.writeFile(filaBrat, rotiAnterior + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    robot.setServoPosition(0.74);
                    bratAnterior = 'x';
                    break;

                case 'b':
                    if(bratAnterior == '0') {
                        ReadWriteFile.writeFile(filaBrat, rotiAnterior + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    robot.setServoPosition(1);
                    bratAnterior = 'b';
                    break;

                default:
                    if(bratAnterior != '0') {
                        ReadWriteFile.writeFile(filaBrat, bratAnterior + " " + runtime.milliseconds() + '\n');
                        runtime.reset();
                    }
                    bratAnterior = '0';
                    robot.bratRobot.setPower(0);
            }
        }
        stop();
    }

    public int directieRoti() {
        if (gamepad1.y)
            return 'y';

        if (gamepad1.a)
            return 'a';

        if (gamepad1.x)
            return 'x';

        if (gamepad1.b)
            return 'b';

        return '0';
    }

    public int manevraBrat() {
        if (gamepad2.y)
            return 'y';

        if (gamepad2.a)
            return 'a';

        if (gamepad2.x)
            return 'x';

        if (gamepad2.b)
            return 'b';

        return '0';
    }
}