import { configureStore } from '@reduxjs/toolkit'
import authReducer from './slices/auth.ts'
import activateReducer from './slices/activate.ts'
import personalReducer from './slices/personal.ts'
import resetReducer from './slices/reset.ts'
import premiumReducer from './slices/premium.ts'
import premiumBuyReducer from './slices/premiumBuy.ts'
import premiumLoginReducer from './slices/premiumLogin.ts'
import premiumPaymentFailedReducer from './slices/premiumPaymentFailed.ts'
import reservationReducer from './slices/reservation.ts'
import reservationBuy from './slices/reservationBuy.ts'
import reservationPersonal from './slices/reservationPersonal.ts'
import reservationPaymentFailed from './slices/reservationPaymentFailed.ts'
import loadingReducer from './slices/loading.ts'

const store = configureStore({
    reducer: {
        auth: authReducer,
        activate: activateReducer,
        personal: personalReducer,
        reset: resetReducer,
        premium: premiumReducer,
        premiumBuy: premiumBuyReducer,
        premiumLogin: premiumLoginReducer,
        premiumPaymentFailed: premiumPaymentFailedReducer,
        reservation: reservationReducer,
        reservationPersonal: reservationPersonal,
        reservationBuy: reservationBuy,
        reservationPaymentFailed: reservationPaymentFailed,
        loading: loadingReducer
    }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
