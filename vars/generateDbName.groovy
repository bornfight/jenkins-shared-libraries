import com.bornfight.context.ContextRegistry
import com.bornfight.util.RandomDatabaseNameGenerator

def call(String dbName) {
    ContextRegistry.registerDefaultContext(this)

    return new RandomDatabaseNameGenerator(dbName).generate()
}
