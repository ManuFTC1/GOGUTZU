package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Robot: Teleop POV", group="Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    ElapsedTime runtime = new ElapsedTime();

    double acceleratie;
    double pozitieMaini = 0.74;

    public void pornire() {

        telemetry.addLine("S-a trezit Gogu");

        while (runtime.seconds() < 0.5) {//dupa init bratu sus, se desfac mainile, bratu jos
            robot.bratRobot.setPower(0.5);
        }
        robot.setServoPosition(pozitieMaini);

        while (runtime.seconds() < 0.7) {
            robot.bratRobot.setPower(0.5);
        }
    }

    @Override
    public void runOpMode() {

        String modCondus = "normal";
        robot.init(hardwareMap);

        telemetry.addLine("Robot PREGATIT");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            //rotile lui Sergiu
            acceleratie = 0;

            if (modCondus.equals("normal"))
                modNormal();
            else
                modManual();

            if (gamepad1.left_bumper)
                modCondus = "manual";
            if (gamepad1.right_bumper)
                modCondus = "normal";

            //bratul lui Manu
            robot.bratRobot.setPower(gamepad2.right_stick_y);

            pozitieMaini += gamepad2.right_trigger * 0.01;
            pozitieMaini -= gamepad2.left_trigger * 0.01;

            pozitieMaini = Math.max(pozitieMaini, 0.25);//sa nu depaseaca valori de 1 sau -1
            pozitieMaini = Math.min(pozitieMaini, 1);

            if (gamepad2.right_bumper)
                pozitieMaini = 1.0;
            if (gamepad2.left_bumper)
                pozitieMaini = 0.75;
            if (gamepad2.a)
                pozitieMaini = 0.25;

            robot.setServoPosition(pozitieMaini);

            //telemetry
            chat();
            telemetry.addData("pozitie dreapta: ", robot.manaDreapta.getPosition());
            telemetry.addData("pozitie stanga: ", robot.manaStanga.getPosition());
            telemetry.update();

            sleep(20);//incetineste sa nu fie prea rapid
        }
        stop();
    }

    public void modManual(){//joysticku stang - roata stanga, joysticku drept - roata dreapta
        float rotireaAiurea;

        rotireaAiurea = gamepad1.left_stick_x;

        if(rotireaAiurea != 0) {
            rotirePeLoc(rotireaAiurea);
            return;
        }

        double vitezaStanga;
        double vitezaDrepta;

        vitezaStanga = -gamepad1.left_stick_y;
        vitezaDrepta = -gamepad1.right_stick_y;

        if(gamepad1.right_trigger == 0 ) {
            robot.motorStanga.setPower(Math.min(vitezaStanga * acceleratie, 0.75));
            robot.motorDreapta.setPower(Math.min(vitezaDrepta * acceleratie, 0.75));
        }
    }

    public void modNormal() {//rt viteza, lt marsalier/frana, joystick stanga directie

        double viteza;
        double viraj;
        double rotireAiurea;

        rotireAiurea = gamepad1.left_stick_x;

        if(rotireAiurea != 0 ) {
            rotirePeLoc(rotireAiurea);
            return;
        }
        viteza = gamepad1.right_trigger - gamepad1.left_trigger;
        viraj = gamepad1.left_stick_x;

        if(viteza == 0)
            putereAmbeleMotoare(0);

        if(viraj == 0)
            putereAmbeleMotoare(viteza);
        else {
            if (viraj < 0) {
                franareDreapta(viteza, viraj);
                putereMotorDreapta(viteza);
            }
            if (viraj > 0) {
                franareStanga(viteza, viraj);
                putereMotorStanga(viteza);
            }
        }
        telemetry.addData("viteza: ", viteza);

    }

    public void putereMotorStanga(double viteza) {
        acceleratie += viteza /23;//numar mai pica => acceleratie mai rapida

        viteza = viteza + acceleratie;

        robot.motorStanga.setPower(viteza);
    }

    public void putereMotorDreapta(double viteza) {
        acceleratie += viteza /23;//numar mai pica => acceleratie mai rapida

        viteza = viteza + acceleratie;

        robot.motorDreapta.setPower(viteza + acceleratie);
    }

    public void franareStanga(double viteza, double viraj) {
        double putereFrana;

        if(viteza > 0) {
            putereFrana = viteza - viraj;
            if(putereFrana < 0.1) {
                putereFrana = -0.1;
            }
        }
        else {
            putereFrana = viteza + viraj;
            if(putereFrana >-0.1)
                putereFrana = 0.1;
        }
        robot.motorStanga.setPower(putereFrana * acceleratie);
    }

    public void franareDreapta(double viteza, double viraj) {
        double putereFrana;

        if(viteza > 0) {
            putereFrana = viteza + viraj;
            if(putereFrana < 0.1) {
                putereFrana = -0.1;
            }
        }
        else {
            putereFrana = viteza - viraj;
            if(putereFrana >-0.1)
                putereFrana = 0.1;
        }
        robot.motorDreapta.setPower(putereFrana * acceleratie);
    }

    public void rotirePeLoc(double viteza) {//se roteste pe loc de pe joysticku din dreapta
        robot.motorStanga.setPower(-viteza);
        robot.motorDreapta.setPower(viteza);
    }

    public void chat() {
        if(gamepad1.dpad_up)
            telemetry.addLine("Calculated!");
        if(gamepad1.dpad_down)
            telemetry.addLine("Faking.");
        if(gamepad1.dpad_left)
            telemetry.addLine("What a save!");
        if(gamepad1.dpad_right)
            telemetry.addLine("Wow!");
    }

    public void putereAmbeleMotoare(double viteza) {
        putereMotorDreapta(viteza);
        putereMotorStanga(viteza);
    }
}