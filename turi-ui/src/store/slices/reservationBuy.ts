import { createSlice } from '@reduxjs/toolkit'
import { RESERVATION_BUY_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface ReservationBuyState {
    isReservationBuy: boolean
}

const initialState: ReservationBuyState = {
    isReservationBuy: false
}

const reservationBuy = createSlice({
    name: RESERVATION_BUY_SLICE,
    initialState,
    reducers: {
        buyReservation(state) {
            state.isReservationBuy = true
        },
        notBuyReservation(state) {
            state.isReservationBuy = false
        }
    }
})

export const { buyReservation, notBuyReservation } = reservationBuy.actions

export const useReservationBuy = () => useSelector((state: RootState) => state.reservationBuy.isReservationBuy)

export default reservationBuy.reducer
