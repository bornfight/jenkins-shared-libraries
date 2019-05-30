def call() {
    junit 'tests/_output/report.xml'
    step([
            $class: 'CloverPublisher',
            cloverReportDir: 'tests/_output/coverage',
            cloverReportFileName: '../coverage.xml',
            healthyTarget: [methodCoverage: 50, conditionalCoverage: 50, statementCoverage: 50],
            unhealthyTarget: [methodCoverage: 0, conditionalCoverage: 0, statementCoverage: 0],
            failingTarget: [methodCoverage: 0, conditionalCoverage: 0, statementCoverage: 0]]
    )
    publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'tests/_output', reportFiles: 'report.html,coverage/index.html', reportName: 'Codeception report', reportTitles: 'Test overview,Code coverage'])
}