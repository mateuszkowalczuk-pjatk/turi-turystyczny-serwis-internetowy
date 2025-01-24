import { createSlice } from '@reduxjs/toolkit'
import { RESERVATION_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface ReservationState {
    isReservation: boolean
}

const initialState: ReservationState = {
    isReservation: false
}

const reservation = createSlice({
    name: RESERVATION_SLICE,
    initialState,
    reducers: {
        reserving(state) {
            state.isReservation = true
        },
        notReserving(state) {
            state.isReservation = false
        }
    }
})

export const { reserving, notReserving } = reservation.actions

export const useReservation = () => useSelector((state: RootState) => state.reservation.isReservation)

export default reservation.reducer
