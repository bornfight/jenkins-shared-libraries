package com.bornfight.context

import com.bornfight.IStepExecutor

interface IContext {
    IStepExecutor getStepExecutor()
}
