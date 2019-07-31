package com.bornfight.context

import com.bornfight.IStepExecutor
import com.bornfight.StepExecutor

class DefaultContext implements IContext, Serializable {
    private steps

    DefaultContext(steps) {
        this.steps = steps
    }

    @Override
    IStepExecutor getStepExecutor() {
        return new StepExecutor(this.steps)
    }
}