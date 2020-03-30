import com.bornfight.context.ContextRegistry

import java.text.SimpleDateFormat

def call(String timezone="UTC") {
    ContextRegistry.registerDefaultContext(this)

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
    sdf.setTimeZone(TimeZone.getTimeZone(timezone))

    return sdf.format(new Date())
}