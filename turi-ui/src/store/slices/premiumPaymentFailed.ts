import { createSlice } from '@reduxjs/toolkit'
import { PREMIUM_PAYMENT_FAILED_SLICE } from '../types.ts'

interface PremiumPaymentFailedState {
    isPremiumPaymentFailed: boolean
}

const initialState: PremiumPaymentFailedState = {
    isPremiumPaymentFailed: false
}

const premiumPaymentFailed = createSlice({
    name: PREMIUM_PAYMENT_FAILED_SLICE,
    initialState,
    reducers: {
        paymentPremiumFailed(state) {
            state.isPremiumPaymentFailed = true
        },
        notPaymentPremiumFailed(state) {
            state.isPremiumPaymentFailed = false
        }
    }
})

export const { paymentPremiumFailed, notPaymentPremiumFailed } = premiumPaymentFailed.actions

export default premiumPaymentFailed.reducer
