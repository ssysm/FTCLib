package com.arcrobotics.ftclib.hardware.motors;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;

public class SimpleServo implements ServoEx {

    Servo servo;
    double maxAngle, minAngle;
    final double maxPosition = 1;
    final double minPosition = 0;

    public SimpleServo(HardwareMap hw, String servoName) {
        servo = hw.get(Servo.class, servoName);
        maxAngle = 180;
        minAngle = 0;
    }

    public SimpleServo(HardwareMap hw, String servoName, double maxAngle, double minAngle) {
        servo = hw.get(Servo.class, servoName);
        this.maxAngle = maxAngle;
        this.minAngle = minAngle;
    }


    @Override
    public void rotateDegrees(double angle) {
        angle = getAngle() + angle;
        turnToAngle(angle);

    }

    @Override
    public void turnToAngle(double angle) {
        if(angle > maxAngle)
            angle = maxAngle;
        else if(angle < minAngle)
            angle = minAngle;

        setPosition((angle - minAngle) / (getAngleRange()));
    }

    @Override
    public void rotate(double position) {
        position = getPosition() + position;
        setPosition(position);
    }

    @Override
    public void setPosition(double position) {
        if(position > maxPosition)
            servo.setPosition(maxPosition);
        else if(position < minAngle)
            servo.setPosition(minPosition);
        else
            servo.setPosition(position);
    }

    @Override
    public void setRange(double min, double max) {
        this.minAngle = min;
        this.maxAngle = max;
    }

    @Override
    public void setInverted(boolean isInverted) {
        if(isInverted)
            servo.setDirection(Servo.Direction.REVERSE);
        else
            servo.setDirection(Servo.Direction.FORWARD);

    }

    @Override
    public boolean getInverted() {
        if(Servo.Direction.REVERSE == servo.getDirection())
            return true;
        else
            return false;
    }

    @Override
    public double getPosition() {
        return servo.getPosition();
    }

    @Override
    public double getAngle() {
        return getPosition() * getAngleRange() + minAngle;
    }

    public double getAngleRange() {
        return maxAngle - minAngle;
    }

}