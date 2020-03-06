import com.bornfight.context.ContextRegistry
import com.bornfight.step.CodeceptionReport
import com.bornfight.step.PortFinder

/**
 * Finds available port
 * @return
 */
def call() {
    ContextRegistry.registerDefaultContext(this)

    def portFinder = new PortFinder()
    return portFinder.getAvailablePort()
}