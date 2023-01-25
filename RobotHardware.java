/* Copyright (c) 2022 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware {

    public DcMotor motorStanga  = null;
    public DcMotor motorDreapta = null;

    public DcMotor bratRobot = null;
    public Servo manaDreapta = null;
    public Servo manaStanga  = null;
    com.qualcomm.robotcore.hardware.HardwareMap hwMap = null;

    public void init(com.qualcomm.robotcore.hardware.HardwareMap ahwMap) {

        hwMap = ahwMap;

        //Roti Robot
        motorStanga = hwMap.get(DcMotor.class, "LeftDrive");
        motorDreapta = hwMap.get(DcMotor.class, "RightDrive");

        motorStanga.setDirection(DcMotor.Direction.FORWARD);
        motorDreapta.setDirection(DcMotor.Direction.REVERSE);

        setWheelsPower(0);

        motorStanga.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorDreapta.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Brat Robot
        bratRobot = hwMap.get(DcMotor.class, "Arm");
        bratRobot.setDirection(DcMotor.Direction.FORWARD);
        bratRobot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bratRobot.setPower(0);

        //Maini Robot
        manaStanga = hwMap.get(Servo.class, "manaStanga");
        manaDreapta = hwMap.get(Servo.class, "manaDreapta");

        manaStanga.setDirection(Servo.Direction.FORWARD);
        manaDreapta.setDirection(Servo.Direction.REVERSE);

        manaStanga.setPosition(0.25);
        manaDreapta.setPosition(0.25);


    }

    public void setWheelsPower(double allPower) {
        motorStanga.setPower(allPower);
        motorDreapta.setPower(allPower);
    }

    public void setServoPosition(double allPosition) {
        manaStanga.setPosition(allPosition);
        manaDreapta.setPosition(allPosition);
    }
}