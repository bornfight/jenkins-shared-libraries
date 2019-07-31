package com.bornfight.step

import com.bornfight.IStepExecutor
import com.bornfight.context.ContextRegistry

class CodeMetricsReport implements Serializable {
    private String branches
    private String credentials
    private String url
    private String checkoutDir

    CodeMetricsReport(String branches, String credentials, String url, String checkoutDir){
        this.branches = branches
        this.credentials = credentials
        this.url = url
        this.checkoutDir = checkoutDir
    }

    void checkout() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        steps.githubCheckout(branches, credentials, url, checkoutDir)
    }

    void installDependencies(){
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.sh("composer install")
    }

    void collect(String repoName){
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.sh("bin/console collect " + repoName + " ../tests/_output/report.xml")
    }
}
