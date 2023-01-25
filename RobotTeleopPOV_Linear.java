/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Robot: Teleop POV", group="Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    private ElapsedTime runtime = new ElapsedTime();

    double pozitieMaini = 0.74;

    @Override
    public void runOpMode() {

        String modCondus = "normal";
        String modBrate = "normal";

        robot.init(hardwareMap);

        telemetry.addLine("Robot PREGATIT");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            //rotile lui Sergiu
            if(modCondus.equals("normal"))
                modNormal();
            else
                modManual();

            if (gamepad1.left_bumper)
                modCondus = "manual";
            if(gamepad1.right_bumper)
                modCondus = "normal";

            //bratul lui Manu
            robot.bratRobot.setPower(gamepad2.right_stick_y);

            if(modBrate == "normal") {
                pozitieMaini += gamepad2.right_trigger * 0.01;
                pozitieMaini -= gamepad2.left_trigger * 0.01;

                pozitieMaini = Math.max(pozitieMaini, 0.75);

                if (gamepad2.right_bumper)
                    pozitieMaini = 1.0;
                if (gamepad2.left_bumper)
                    pozitieMaini = 0.75;

                robot.setServoPosition(pozitieMaini);
            }
            else {
                robot.setServoPosition(0.25);
            }

            if(gamepad2.dpad_up)
                modBrate = "normal";

            if(gamepad2.dpad_down)
                modBrate = "stop";

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
        float rotire1;
        float rotire2;
        float rotireaAiurea;

        rotireaAiurea = gamepad1.left_stick_x;

        if(rotireaAiurea != 0) {
            rotirePeLoc(rotireaAiurea);
            return;
        }

        float vitezaStanga;
        float vitezaDrepta;

        rotire1 = gamepad1.left_stick_x;
        rotire2 = gamepad1.right_stick_x;

        vitezaStanga = -gamepad1.left_stick_y;
        vitezaDrepta = -gamepad1.right_stick_y;

        if(gamepad1.right_trigger == 0 ) {
            robot.motorStanga.setPower(Math.min(vitezaStanga, 0.75));
            robot.motorDreapta.setPower(Math.min(vitezaDrepta, 0.75));
        }
    }

    public void modNormal() {//rt viteza, lt marsalier/frana, joystick stanga directie

        float viteza;
        float viraj;
        float rotireAiurea;

        rotireAiurea = gamepad1.left_stick_x;

        if(rotireAiurea != 0 ) {
            rotirePeLoc(rotireAiurea);
            return;
        }
        viteza = gamepad1.right_trigger - gamepad1.left_trigger;
        viraj = gamepad1.left_stick_x;

        if(viteza == 0)
            robot.setWheelsPower(0);

        if(viraj == 0)
            robot.setWheelsPower(viteza);
        else {
            if (viraj < 0) {
                franareDreapta(viteza, viraj);
                robot.motorDreapta.setPower(viteza);
            }
            if (viraj > 0) {
                franareStanga(viteza, viraj);
                robot.motorStanga.setPower(viteza);
            }
        }
        telemetry.addData("viteza: ", viteza);

    }

    public void franareStanga(float viteza, float viraj) {
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
        robot.motorStanga.setPower(putereFrana);
    }

    public void franareDreapta(float viteza, float viraj) {
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
        robot.motorDreapta.setPower(putereFrana);
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

    public void rotirePeLoc(float viteza) {//se roteste pe loc de pe joysticku din dreapta
        robot.motorStanga.setPower(-viteza);
        robot.motorDreapta.setPower(viteza);
    }
}
