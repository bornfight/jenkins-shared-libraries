import com.bornfight.context.ContextRegistry
import com.bornfight.step.TestStep

def call(String message) {
    ContextRegistry.registerDefaultContext(this)

    def testStep = new TestStep(message)
    testStep.execute()
}