package com.bornfight.step

import com.bornfight.IStepExecutor
import com.bornfight.context.ContextRegistry

class CodeceptionReport implements Serializable {
    private String testResultsDirPath = "tests/_output"
    private int[][] targetValues
    private boolean allowEmpty

    CodeceptionReport(boolean allowEmpty, int[][] targetValues){
        this.targetValues = targetValues
        this.allowEmpty = allowEmpty
    }

    void execute() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        
        steps.sh("touch .env.test.local")
        steps.junit(allowEmpty, testResultsDirPath+"/report.xml")
        steps.publishTestCoverage(targetValues)
        steps.publishTestResults()
    }
}
