/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.TouchSensor;

/*
 * This is an example LinearOpMode that shows how to use
 * the Modern Robotics ITR Seeker
 *
 * The op mode assumes that the IR Seeker
 * is configured with a name of "ir seeker".
 *
 * Set the switch on the Modern Robotics IR beacon to 1200 at 180.  <br>
 * Turn on the IR beacon.
 * Make sure the side of the beacon with the LED on is facing the robot. <br>
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "Ir Seeker", group = "Sensor")
//@Disabled
public class IrSeeker extends LinearOpMode {

    DcMotor leftMotor1 = null;
    DcMotor leftMotor2 = null;
    DcMotor rightMotor1 = null;
    DcMotor rightMotor2 = null;

  @Override
  public void runOpMode() throws InterruptedException {

      IrSeekerSensor irSeeker;    // Hardware Device Object

      leftMotor1  = hardwareMap.dcMotor.get("leftMotor1");
      leftMotor2  = hardwareMap.dcMotor.get("leftMotor2");
      rightMotor1 = hardwareMap.dcMotor.get("rightMotor1");
      rightMotor2 = hardwareMap.dcMotor.get("rightMotor2");

      leftMotor1.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
      leftMotor2.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
      rightMotor1.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
      rightMotor2.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

    // get a reference to our GyroSensor object.
    irSeeker = hardwareMap.irSeekerSensor.get("seeker");

      double irAngle;
      double irStrength;
    // wait for the start button to be pressed.
    waitForStart();

    while (opModeIsActive())  {

        irAngle = irSeeker.getAngle();
        irStrength = irSeeker.getStrength();

      // Ensure we have a IR signal
      if (irSeeker.signalDetected())
      {
        // Display angle and strength
        telemetry.addData("Angle",    irSeeker.getAngle());
        telemetry.addData("Strength", irSeeker.getStrength());

          if (irStrength < 0.4) {

              if (irAngle >= 8) {
                  leftMotor1.setPower(-.5);
                  leftMotor2.setPower(.5);
                  rightMotor1.setPower(.5);
                  rightMotor2.setPower(-.5);
              }
              if (irAngle < -8) {
                  leftMotor1.setPower(.5);
                  leftMotor2.setPower(-.5);
                  rightMotor1.setPower(-.5);
                  rightMotor2.setPower(.5);
              }

              if (irAngle >= -8 ^ irAngle <= 8) {
                  leftMotor1.setPower(.5);
                  leftMotor2.setPower(.5);
                  rightMotor1.setPower(.5);
                  rightMotor2.setPower(.5);
              }
          }
          if (irStrength > 0.4) {
              leftMotor1.setPower(0);
              leftMotor2.setPower(0);
              rightMotor1.setPower(0);
              rightMotor2.setPower(0);
          }
      }
      else
      {
        // Display loss of signal
        telemetry.addData("Seeker", "Signal Lost");
          leftMotor1.setPower(-0.5);
          leftMotor2.setPower(-0.5);
          rightMotor1.setPower(0.5);
          rightMotor2.setPower(0.5);
      }


      telemetry.update();
      idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
    }
  }
}
