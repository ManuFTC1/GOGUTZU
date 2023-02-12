package org.firstinspires.ftc.LTASrob.OpModes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.LTASrob.RobotHardware;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="TELE: Joc La Nivel Inalt", group="Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    ElapsedTime runtime = new ElapsedTime();

    double acceleratieMotorDreapta = 0;
    double acceleratieMotorStanga = 0;
    double pozitieMaini = 0.25;
    double variabilaAcceleratie = 15;
    @Override
    public void runOpMode() {

        String modCondus = "normal";
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            //rotile lui Sergiu
            if (gamepad1.left_trigger != 0 || gamepad1.right_trigger != 0)
                modCondus = "normal";
            if (gamepad1.left_stick_y < -0.5 || gamepad1.left_stick_y > 0.50 || gamepad1.right_stick_y < -0.5 || gamepad1.right_stick_y > 0.50)
                modCondus = "manual";

            robot.brakeWheels(gamepad1.a);//pune frana daca apesi pe a

            if(!gamepad1.a)
                if (modCondus.equals("normal"))
                    modNormal();
                else
                    modManual();


            //bratul lui Manu
            robot.bratRobot.setPower(gamepad2.left_stick_y);

            pozitieMaini += gamepad2.right_trigger * 0.07;
            pozitieMaini -= gamepad2.left_trigger * 0.07;

            pozitieMaini = Math.max(pozitieMaini, 0.25);//nu depaseste valori de 1 sau 0.25
            pozitieMaini = Math.min(pozitieMaini, 1);

            if (gamepad2.left_bumper)
                pozitieMaini = 1.0;
            if (gamepad2.right_bumper)
                pozitieMaini = 0.74;
            if (gamepad2.a)
                pozitieMaini = 0.25;

            robot.setServoPosition(pozitieMaini);

            //telemetry

            telemetry.addData("senzor: ", robot.senzorul.getDistance(DistanceUnit.CM));
            telemetrieMaini();

        }
        stop();//sa fim siguri ca se opreste
    }

    public void modManual(){//joysticku stang - roata stanga, joysticku drept - roata dreapta
        robot.motorDreapta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.motorStanga.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        double inputDreapta = gamepad1.right_stick_y;
        double inputStanga  = gamepad1.left_stick_y;
        float vitezaMaxima = 0.8f;

        if(inputDreapta > vitezaMaxima)
            inputDreapta = vitezaMaxima;
        if(inputDreapta < -vitezaMaxima)
            inputDreapta = -vitezaMaxima;

        if(inputStanga > vitezaMaxima)
            inputStanga = vitezaMaxima;
        if(inputStanga < -vitezaMaxima)
            inputStanga = -vitezaMaxima;

        robot.motorDreapta.setPower(-inputDreapta);
        robot.motorStanga.setPower(-inputStanga);
    }

    public void modNormal() {//rt viteza, lt marsalier/frana, joystick stanga directie

        double viteza;
        double viraj;

        viraj = gamepad1.left_stick_x;

        viteza = gamepad1.right_trigger - gamepad1.left_trigger;

        robot.motorDreapta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.motorStanga.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

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
        if(viteza > 0.8)
            viteza = 0.8;
        if(viteza < -0.8)
            viteza = -0.8;

        if(viteza * acceleratieMotorStanga < 0){ //daca au semne diferite
            acceleratieMotorStanga = 0;
        }

        acceleratieMotorStanga += viteza / variabilaAcceleratie; //acceleratie mai rapida daca nr este mai mic

        if(acceleratieMotorStanga > 1) //limiteaza acceleratia intr 1 si -1
            acceleratieMotorStanga = 1;

        if(acceleratieMotorStanga < -1)
            acceleratieMotorStanga = -1;

        if(viteza > acceleratieMotorStanga)
            robot.motorStanga.setPower(Math.min(viteza, acceleratieMotorStanga));
        else
            robot.motorStanga.setPower(Math.max(viteza, acceleratieMotorStanga));

        telemetry.addData("Motor stanga viteza: ", viteza);
        telemetry.addData("Motor stanga acceleratie: ", acceleratieMotorStanga);
    }

    public void putereMotorDreapta(double viteza) {
        if(viteza > 0.8)
            viteza = 0.8;
        if(viteza < -0.8)
            viteza = -0.8;

        if(viteza * acceleratieMotorDreapta <= 0){
            acceleratieMotorDreapta = 0;
        }

        acceleratieMotorDreapta += viteza / variabilaAcceleratie;

        if(acceleratieMotorDreapta > 1)
            acceleratieMotorDreapta = 1;

        if(acceleratieMotorDreapta < -1)
            acceleratieMotorDreapta = -1;

        if(viteza > acceleratieMotorDreapta)
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
