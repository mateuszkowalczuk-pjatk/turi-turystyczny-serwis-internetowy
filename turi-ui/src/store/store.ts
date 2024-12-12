import { configureStore } from '@reduxjs/toolkit'
import authReducer from './slices/auth.ts'
import activateReducer from './slices/activate.ts'
import personalReducer from './slices/personal.ts'
import resetReducer from './slices/reset.ts'
import premiumReducer from './slices/premium.ts'
import premiumBuyReducer from './slices/premium-buy.ts'
import premiumLoginReducer from './slices/premium-login.ts'
import premiumPaymentFailedReducer from './slices/premium-payment-failed.ts'

const store = configureStore({
    reducer: {
        auth: authReducer,
        activate: activateReducer,
        personal: personalReducer,
        reset: resetReducer,
        premium: premiumReducer,
        premiumBuy: premiumBuyReducer,
        premiumLogin: premiumLoginReducer,
        premiumPaymentFailed: premiumPaymentFailedReducer
    }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
