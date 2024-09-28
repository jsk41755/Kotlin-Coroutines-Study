package com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.homework.assignmenttwo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class MoneyTransferViewModel : ViewModel() {

    var state by mutableStateOf(MoneyTransferState())
        private set

    private var job: Job? = null

    fun onAction(action: MoneyTransferAction) {
        when (action) {
            MoneyTransferAction.TransferFunds -> transferFunds()
            MoneyTransferAction.CancelTransfer -> {
                job?.cancel()
                state = state.copy(
                    isTransferring = false,
                    processingState = null,
                    resultMessage = null,
                )
            }
        }
    }

    private fun transferFunds() {
        job = viewModelScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    state = state.copy(
                        isTransferring = true,
                        resultMessage = null,
                    )
                    val amountToTransfer = state.transferAmount.text.toString().toDoubleOrNull()

                    if (amountToTransfer == null) {
                        state = state.copy(
                            resultMessage = "Invalid Amount"
                        )
                        return@withContext
                    }

                    if (amountToTransfer == 0.0) {
                        state = state.copy(
                            resultMessage = "Enter amount greater than 0"
                        )
                        return@withContext
                    }

                    state = state.copy(
                        processingState = ProcessingState.CheckingFunds,
                    )
                    ensureActive() // TODO 1 done
                    val hasEnoughFunds = checkHasEnoughFunds(state.savingsBalance, amountToTransfer)

                    if (!hasEnoughFunds) {
                        state = state.copy(
                            resultMessage = "Insufficient funds",
                        )
                        return@withContext
                    }
                    if (job?.isActive == true) { // TODO 2 done
                        val debitDeferred =
                            async {
                                debitAccount(state.savingsBalance, amountToTransfer)
                            }
                        val creditDeferred =
                            async {
                                creditAccount(state.checkingBalance, amountToTransfer)
                            }
                        state = state.copy(
                            resultMessage = "Transfer complete!",
                        )
                    }
                } catch (e: Exception) {
                    if (e is CancellationException) {
                        println("Cancellation exception is called: ${e.message}")
                    }
                    println("Error processing transfer: ${e.message}")
                } finally {
                    withContext(NonCancellable) { // TODO 3 done
                        cleanupResources()
                    }
                    state = state.copy(
                        processingState = null,
                        isTransferring = false,
                    )
                }
            }
        }
    }

    private suspend fun creditAccount(toAccountBalance: Double, amount: Double) {
        state = state.copy(
            processingState = ProcessingState.CreditingAccount,
        )
        println("Crediting account with balance $toAccountBalance and amount $amount")
        delay(3000)
        state = state.copy(
            checkingBalance = toAccountBalance + amount,
        )
    }

    private suspend fun debitAccount(fromAccountBalance: Double, amount: Double) {
        state = state.copy(processingState = ProcessingState.DebitingAccount)
        delay(3000)
        state = state.copy(
            savingsBalance = fromAccountBalance - amount,
        )
    }

    private suspend fun checkHasEnoughFunds(fromAccountBalance: Double, amount: Double): Boolean {
        println("Checking balance with balance $fromAccountBalance and amount $amount")
        delay(2000)
        return amount <= fromAccountBalance
    }

    private suspend fun cleanupResources() {
        state = state.copy(
            processingState = ProcessingState.CleanupResources,
        )
        delay(2000)
    }
}