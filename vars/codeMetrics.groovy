import com.bornfight.context.ContextRegistry
import com.bornfight.step.CodeMetricsReport

def call(String repoName) {
    ContextRegistry.registerDefaultContext(this)

    def cmr = new CodeMetricsReport("*/master", "istudio", "git@github.com:degordian/code-metrics-observatory.git", "code-metrics-observatory")
    cmr.checkout()
    dir('code-metrics-observatory') {
        cmr.installDependencies()
        configFileProvider([configFile(fileId: 'c3d7339a-64c3-43ed-a9f0-51a2423f267c', targetLocation: '.env.test.local')]) {
            cmr.collect(repoName)
        }
    }
}
