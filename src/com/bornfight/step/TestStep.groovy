package com.bornfight.step

import com.bornfight.context.ContextRegistry
import com.bornfight.IStepExecutor

class TestStep implements Serializable {
    private String message

    TestStep(String message) {
        this.message = message
    }

    void execute() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        int status = steps.sh("echo \"" + message + "\"")
        if(status != 0) {
            steps.error("Error happened")
        }
    }

}
