package com.bornfight.context

class ContextRegistry implements Serializable {
    private static IContext context

    static void registerContext(IContext context) {
        this.context = context
    }

    static void registerDefaultContext(Object steps) {
        context = new DefaultContext(steps)
    }

    static IContext getContext() {
        return context
    }
}
