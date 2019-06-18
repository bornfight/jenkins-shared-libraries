/**
 * Publishes code coverage report
 * @param targets - 3x3 matrix which contains target values (0-100)
 * @return
 */
def call(int[][] targets) {
    junit 'tests/_output/report.xml'
    step([
            $class: 'CloverPublisher',
            cloverReportDir: 'tests/_output/coverage',
            cloverReportFileName: '../coverage.xml',
            healthyTarget: [methodCoverage: targets[0][0], conditionalCoverage: targets[0][1], statementCoverage: targets[0][2]],
            unhealthyTarget: [methodCoverage: targets[1][0], conditionalCoverage: targets[1][1], statementCoverage: targets[1][2]],
            failingTarget: [methodCoverage: targets[2][0], conditionalCoverage: targets[2][1], statementCoverage: targets[2][2]],
    ])
    publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'tests/_output', reportFiles: 'report.html,coverage/index.html', reportName: 'Codeception report', reportTitles: 'Test overview,Code coverage'])
}