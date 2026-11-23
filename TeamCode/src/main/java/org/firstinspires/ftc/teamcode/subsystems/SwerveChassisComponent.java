package org.firstinspires.ftc.teamcode.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.firstinspires.ftc.teamcode.math.AngularOperations;
import org.firstinspires.ftc.teamcode.math.VectorOperations;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SwerveChassisComponent extends ChassisComponent {

    private final SwerveModule[] swerveModules;
    private volatile boolean driveMethodFinished = true, rotationRequested = false;
    private final ExecutorService swerveDriveOperator = Executors.newSingleThreadExecutor();
    private boolean isDrivingEnabled = true;
    private volatile SwerveNode beginnerSwerveNode;
    private final ConcurrentHashMap<MotorExWrapper, Double> motorToMotorPowerMultiplier = new ConcurrentHashMap<>();

    public SwerveChassisComponent(SwerveModule... swerveModules) {
        this.swerveModules = swerveModules;
    }

    @Override
    public void drive() {
        if(driveMethodFinished) {
            driveMethodFinished = false;
        } else {
            System.out.println("This method has not finished yet");
            return;
        }

        double forward = getLeftY(), sideward = getLeftX(), rotation = getRightX();
        rotationRequested = rotation != 0;
        VectorOperations.Vector newHeadingVector = rotationRequested ? new VectorOperations.Vector(new LinkedList<>(List.of(0.0, 0.0, rotation)))
                : new VectorOperations.Vector(new LinkedList<>(List.of(sideward, forward)));

        swerveDriveOperator.submit(() -> {
            beginnerSwerveNode = new SwerveNode(() -> {
                /*Step 1: Calculate positions for each module and then set their positions.*/
                List<MotorExWrapper> motorExWrappers = new ArrayList<>();

                for(SwerveModule swerveModule : swerveModules) {
                    MotorExWrapper turningMotor = swerveModule.getTurningMotor();
                    double currentMotorPositionDegrees = turningMotor.getCurrentPosition() / turningMotor.getCPR() * 360;
                    double newHeadingPositionDegrees = AngularOperations.angleInRelationToXAxis(newHeadingVector);
                    DoublePair optimizedHeadingPair = optimizeHeading(
                            currentMotorPositionDegrees, newHeadingPositionDegrees
                    );
                    motorToMotorPowerMultiplier.put(swerveModule.getDrivingMotor(), optimizedHeadingPair.getD2() * newHeadingVector.magnitude());
                    setTargetPositionForTurningMotor(optimizedHeadingPair.getD1(), turningMotor);
                    motorExWrappers.add(turningMotor);

                    System.out.println("Turning motor target position: " + getTargetPositionForTurningMotor(turningMotor));
                    System.out.println("Bruh this is running.");
                    System.out.println("Motor is at setpoint: " + (pidSystemAtSetPointForTurningMotor(turningMotor)));

                }


                do {
                   try {
                       for(SwerveModule swerveModule : swerveModules) {
                           MotorExWrapper turningMotor = swerveModule.getTurningMotor();
                           double calculate = getCalculateForTurningMotor(turningMotor);
                           turningMotor.set(calculate);
                           System.out.println("Turning motor current position: " + turningMotor.getCurrentPosition() + "\t Calculate: " + calculate);
                       }
                     Thread.sleep(300);
                   } catch(Exception e) {
                       throw new RuntimeException(e);
                   }
                } while(!pidSystemAtSetPointForTurningMotors(motorExWrappers));

                System.out.println("Done. Ran it.");
            });

            beginnerSwerveNode.setNext(new SwerveNode(() -> {
                for(SwerveModule module : swerveModules) {
                    module.getDrivingMotor().set(motorToMotorPowerMultiplier.get(module.getDrivingMotor()));
                }
                driveMethodFinished = true;
            }));

            /*-----------------------------------------------------------------------------------------------------------------------
            * DO NOT CHANGE ANYTHING UNDER THIS LINE!*/

            SwerveNode next = beginnerSwerveNode.getNext();
            beginnerSwerveNode.run();

            while(next != null) {
                next.run();
                next = next.getNext();
            }
            driveMethodFinished = true;
        });


    }

    private DoublePair optimizeHeading(double currentHeading, double newHeading) {
        double optimizedHeading;
        double angularDifference = angularDifferenceDegrees(currentHeading, newHeading, true);
        boolean reverseHeading = false;
        if(isOptimizeForShortestRotation()) {
            reverseHeading = Math.abs(angularDifference) > 90;
            optimizedHeading = reverseHeading ?
                    angularDifference - Math.copySign(180, angularDifference) : angularDifference;
        } else {
            optimizedHeading = angularDifference;
        }
        return new DoublePair(optimizedHeading, reverseHeading ? -1 : 1);
    }

    private static final class DoublePair {
        private final double d1, d2;

        public DoublePair(double d1, double d2) {
            this.d1 = d1;
            this.d2 = d2;
        }

        public double getD1() {
            return d1;
        }

        public double getD2() {
            return d2;
        }
    }

    private double angularDifferenceDegrees(double currentHeading, double newHeading, boolean normalizeTo180) {
        double headingDifference = newHeading - currentHeading;
        return normalizeTo180 ? normalizeHeadingToRange180(headingDifference)
                : normalizeHeadingToRange360(headingDifference);
    }

    private double normalizeHeadingToRange360(double heading) {
        return ((heading % 360) + 360) % 360;
    }

    private double normalizeHeadingToRange180(double heading) {
       double headingNormalizedTo360 = normalizeHeadingToRange360(heading);
       double headingNormalizedTo180 = headingNormalizedTo360 > 180 ?
               -(360 - headingNormalizedTo360) : headingNormalizedTo360;
       return headingNormalizedTo180;
    }

    @Override
    public boolean isDrivingEnabled() {
        return isDrivingEnabled;
    }

    @Override
    public void stopEverything() {
        swerveDriveOperator.shutdownNow();
        for(SwerveModule module : swerveModules) {
            module.getTurningMotor().stopMotor();
            module.getDrivingMotor().stopMotor();
        }
        isDrivingEnabled = false;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    public static final class SwerveModule {
        private final MotorExWrapper turningMotor, drivingMotor;

        public SwerveModule(MotorExWrapper turningMotor, MotorExWrapper drivingMotor) {
            this.turningMotor = turningMotor;
            this.drivingMotor = drivingMotor;
        }

        public MotorExWrapper getTurningMotor() {
            return turningMotor;
        }

        public MotorExWrapper getDrivingMotor() {
            return drivingMotor;
        }
    }

    public static final class SwerveNode {
        private SwerveNode next;
        private final Runnable task;

        public SwerveNode(Runnable task) {
            this.task = task;
        }

        public SwerveNode setNext(SwerveNode next) {
            this.next = next;
            return this.next;
        }

        public SwerveNode getNext() {
            return next;
        }

        public boolean hasNext() {
            return next != null;
        }

        public void run() {
            task.run();
        }
    }
}