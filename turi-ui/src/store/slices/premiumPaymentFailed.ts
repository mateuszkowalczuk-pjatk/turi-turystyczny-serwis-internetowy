import { createSlice } from '@reduxjs/toolkit'
import { PREMIUM_PAYMENT_FAILED_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

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

export const usePremiumPaymentFailed = () =>
    useSelector((state: RootState) => state.premiumPaymentFailed.isPremiumPaymentFailed)

export default premiumPaymentFailed.reducer
