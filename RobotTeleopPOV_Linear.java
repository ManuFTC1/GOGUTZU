package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Robot: Teleop POV", group="Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    ElapsedTime runtime = new ElapsedTime();

    double acceleratieMotorDreapta = 0;
    double acceleratieMotorStanga = 0;
    double pozitieMaini = 0.25;

    @Override
    public void runOpMode() {

        String modCondus = "normal";
        robot.init(hardwareMap);

        telemetry.addLine("Robot PREGATIT");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            //rotile lui Sergiu
            if (gamepad1.left_bumper)
                modCondus = "normal";
            if (gamepad1.right_bumper)
                modCondus = "manual";

            if (modCondus.equals("normal"))
                modNormal();
            else
                modManual();


            //bratul lui Manu
            robot.bratRobot.setPower(gamepad2.right_stick_y);

            pozitieMaini += gamepad2.right_trigger * 0.01;
            pozitieMaini -= gamepad2.left_trigger * 0.01;

            pozitieMaini = Math.max(pozitieMaini, 0.25);//sa nu depaseaca valori de 1 sau -1
            pozitieMaini = Math.min(pozitieMaini, 1);

            if (gamepad2.right_bumper)
                pozitieMaini = 1.0;
            if (gamepad2.left_bumper)
                pozitieMaini = 0.74;
            if (gamepad2.a)
                pozitieMaini = 0.25;

            robot.setServoPosition(pozitieMaini);

            //telemetry
            telemetrieMaini();

        }
        stop();
    }

    public void modManual(){//joysticku stang - roata stanga, joysticku drept - roata dreapta
        double rotireaAiurea;

        if(gamepad1.right_trigger != 0) {// da un reset
            acceleratieMotorDreapta = 0;
            acceleratieMotorStanga = 0;
        }

        rotireaAiurea = gamepad1.left_stick_x;

        if(rotireaAiurea != 0) {
            virajRoti(0, rotireaAiurea);
            return;
        }

        double vitezaStanga;
        double vitezaDrepta;


        vitezaStanga = -gamepad1.left_stick_y;
        vitezaDrepta = -gamepad1.right_stick_y;

        if(vitezaDrepta == 0)
            acceleratieMotorDreapta = 0;

        if(vitezaStanga == 0)
            acceleratieMotorStanga = 0;

        putereMotorStanga(vitezaStanga);
        putereMotorDreapta(vitezaDrepta);
    }

    public void modNormal() {//rt viteza, lt marsalier/frana, joystick stanga directie

        double viteza;
        double viraj;

        viraj = gamepad1.left_stick_x;

        viteza = gamepad1.right_trigger - gamepad1.left_trigger;

        if(viteza == 0) {
            robot.setWheelsPower(0);
            acceleratieMotorStanga = 0;
            acceleratieMotorDreapta = 0;
            return;
        }


        if(viraj != 0 ) {
            virajRoti(viteza, viraj);
        }
        else {
            putereMotorDreapta(viteza);
            putereMotorStanga(viteza);
        }
    }


    public void putereMotorStanga(double viteza) {
        if(viteza > 1)
            viteza = 1;
        if(viteza < -1)
            viteza = -1;

        if(viteza * acceleratieMotorStanga < 0){ //daca au semne diferite
            acceleratieMotorStanga = 0;
        }

        acceleratieMotorStanga += viteza / 20; //acceleratie mai rapida daca nr este mai mic

        if(acceleratieMotorStanga > 1) //limiteaza acceleratia intr 1 si -1
            acceleratieMotorStanga = 1;

        if(acceleratieMotorStanga < -1)
            acceleratieMotorStanga = -1;

        if(viteza > 0)
            robot.motorStanga.setPower(Math.min(viteza, acceleratieMotorStanga));
        else
            robot.motorStanga.setPower(Math.max(viteza, acceleratieMotorStanga));

        telemetry.addData("Motor stanga viteza: ", viteza);
        telemetry.addData("Motor stanga acceleratie: ", acceleratieMotorStanga);
    }

    public void putereMotorDreapta(double viteza) {
        if(viteza > 1)
            viteza = 1;
        if(viteza < -1)
            viteza = -1;

        if(viteza * acceleratieMotorDreapta <= 0){
            acceleratieMotorDreapta = 0;
        }

        acceleratieMotorDreapta += viteza / 20;

        if(acceleratieMotorDreapta > 1)
            acceleratieMotorDreapta = 1;

        if(acceleratieMotorDreapta < -1)
            acceleratieMotorDreapta = -1;

        if(viteza > 0)
            robot.motorDreapta.setPower(Math.min(viteza, acceleratieMotorDreapta));
        else
            robot.motorDreapta.setPower(Math.max(viteza, acceleratieMotorDreapta));

        telemetry.addData("Motor dreapta viteza: ", viteza);
        telemetry.addData("Motor dreapta acceleratie: ", acceleratieMotorDreapta);
    }


    public void virajRoti(double viteza, double viraj) {//se roteste pe loc
        if(viteza >0) {
            if(viraj < 0) {
                putereMotorStanga(Math.max(-0.1, viteza + viraj - 0.1));
                putereMotorDreapta(viteza);
            }
            else {
                putereMotorDreapta(Math.max(-0.1, viteza - viraj - 0.1));
                putereMotorStanga(viteza);
            }
        }

        else {
            if(viraj < 0) {
                putereMotorStanga(Math.min(0.1, viteza - viraj + 0.1));
                putereMotorDreapta(viteza);
            }
            else {
                putereMotorDreapta(Math.min(0.1, viteza + viraj + 0.1));
                putereMotorStanga(viteza);
            }
        }
    }





    public void telemetrieMaini() {
        telemetry.addData("pozitie dreapta: ", robot.manaDreapta.getPosition());
        telemetry.addData("pozitie stanga: ", robot.manaStanga.getPosition());
        telemetry.update();
    }
}