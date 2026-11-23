package org.firstinspires.ftc.teamcode.pid;


import org.firstinspires.ftc.teamcode.subsystems.PIDControllerWrapper;

import java.util.function.DoubleSupplier;

public class PIDControlSystemConstraints {

    private final DoubleSupplier pidInput;
    private final PIDControllerWrapper controllerWrapper;

    private PIDControlSystemConstraints(PIDControlSystemConstraints.Builder builder) {
        pidInput = builder.pidInput;
        controllerWrapper = builder.controllerWrapper;
    }


    public DoubleSupplier getPidInput() {
        return pidInput;
    }

    public PIDControllerWrapper getControllerWrapper() {
        return controllerWrapper;
    }


    public static final class Builder {
        private DoubleSupplier pidInput;
        private PIDControllerWrapper controllerWrapper;

        public Builder setPIDInput(DoubleSupplier pidInput) {
            this.pidInput = pidInput;
            return this;
        }

        public Builder setPIDControllerWrapper(PIDControllerWrapper controllerWrapper) {
            this.controllerWrapper = controllerWrapper;
            return this;
        }

        public PIDControlSystemConstraints build() {
            return new PIDControlSystemConstraints(this);
        }

    }
}
