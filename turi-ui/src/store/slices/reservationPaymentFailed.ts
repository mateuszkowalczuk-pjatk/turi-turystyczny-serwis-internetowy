import { createSlice } from '@reduxjs/toolkit'
import { RESERVATION_PAYMENT_FAILED_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface ReservationPaymentFailedState {
    isReservationPaymentFailed: boolean
}

const initialState: ReservationPaymentFailedState = {
    isReservationPaymentFailed: false
}

const reservationPaymentFailed = createSlice({
    name: RESERVATION_PAYMENT_FAILED_SLICE,
    initialState,
    reducers: {
        reservationPremiumFailed(state) {
            state.isReservationPaymentFailed = true
        },
        notReservationPremiumFailed(state) {
            state.isReservationPaymentFailed = false
        }
    }
})

export const { reservationPremiumFailed, notReservationPremiumFailed } = reservationPaymentFailed.actions

export const useReservationPaymentFailed = () =>
    useSelector((state: RootState) => state.reservationPaymentFailed.isReservationPaymentFailed)

export default reservationPaymentFailed.reducer
