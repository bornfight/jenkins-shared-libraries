import com.bornfight.context.ContextRegistry
import com.bornfight.step.CodeceptionReport

/**
 * Publishes code coverage report
 * @param targets - 3x3 matrix which contains target values (0-100)
 * @return
 */
def call(int[][] targets) {
    ContextRegistry.registerDefaultContext(this)

    def codeceptionReport = new CodeceptionReport(true, targets)
    codeceptionReport.execute()
}