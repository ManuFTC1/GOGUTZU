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

    double fataSpate;
    double stangaDreapta;
    double stangu;
    double dreptu;

    @Override
    public void runOpMode() {

        String modCondus = "normal";

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
            robot.bratRobot.setPower(gamepad2.left_stick_y);

            sleep(50);//incetineste sa nu fie prea rapid
        }

    }

    public void modNormal() {//cu joysticku din stanga se controleaza ambele roti
        fataSpate = -gamepad1.left_stick_y;
        stangaDreapta = gamepad1.left_stick_x;

        stangu = fataSpate + stangaDreapta;
        dreptu = fataSpate - stangaDreapta;

        robot.motorStanga.setPower(stangu);
        robot.motorDreapta.setPower(dreptu);
    }

    public void modManual(){//joysticku stang - roata stanga, joysticku drept - roata dreapta
        robot.motorStanga.setPower(-gamepad1.left_stick_y);
        robot.motorDreapta.setPower(-gamepad1.right_stick_y);
    }
}