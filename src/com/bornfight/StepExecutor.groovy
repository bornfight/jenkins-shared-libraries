package com.bornfight

class StepExecutor implements IStepExecutor {
    private steps

    StepExecutor(steps) {
        this.steps = steps
    }

    @Override
    int sh(String command) {
        steps.sh([returnStatus: true, script: "${command}"])
    }

    @Override
    void error(String message) {
        steps.error(message)
    }

    @Override
    void junit(boolean allowEmpty, String testResults) {
        steps.junit([allowEmptyResults: allowEmpty, testResults: testResults])
    }

    @Override
    void publishTestCoverage(int[][] targetValues) {
        steps.step([
                $class              : 'CloverPublisher',
                cloverReportDir     : 'tests/_output/coverage',
                cloverReportFileName: '../coverage.xml',
                healthyTarget       : [methodCoverage: targetValues[0][0], conditionalCoverage: targetValues[0][1], statementCoverage: targetValues[0][2]],
                unhealthyTarget     : [methodCoverage: targetValues[1][0], conditionalCoverage: targetValues[1][1], statementCoverage: targetValues[1][2]],
                failingTarget       : [methodCoverage: targetValues[2][0], conditionalCoverage: targetValues[2][1], statementCoverage: targetValues[2][2]],
        ])

    }

    @Override
    void publishTestResults() {
        steps.publishHTML([allowMissing: true, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'tests/_output', reportFiles: 'report.html,coverage/index.html', reportName: 'Codeception report', reportTitles: 'Test overview,Code coverage'])
    }

    @Override
    void githubCheckout(String branches, String credentials, String url, String checkoutDir) {
        steps.checkout([$class: 'GitSCM', branches: [[name: branches]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: checkoutDir]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: credentials, url: url]]])
    }
}
