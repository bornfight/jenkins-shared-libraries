import com.bornfight.context.ContextRegistry

import java.text.SimpleDateFormat

def call() {
    ContextRegistry.registerDefaultContext(this)

    String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date())

    return timeStamp
}