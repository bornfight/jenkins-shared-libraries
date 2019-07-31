package com.bornfight

interface IStepExecutor {
    int sh(String command)
    void error(String message)
    void junit(boolean allowEmpty, String testResults)
    void publishTestCoverage(int[][] targetValues)
    void publishTestResults()
    void githubCheckout(String branches, String credentials, String url, String checkoutDir)
}
