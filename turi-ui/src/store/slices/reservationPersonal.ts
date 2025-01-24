import { createSlice } from '@reduxjs/toolkit'
import { RESERVATION_PERSONAL_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface ReservationPersonalState {
    isReservationPersonal: boolean
}

const initialState: ReservationPersonalState = {
    isReservationPersonal: false
}

const reservationPersonal = createSlice({
    name: RESERVATION_PERSONAL_SLICE,
    initialState,
    reducers: {
        personalReservation(state) {
            state.isReservationPersonal = true
        },
        notPersonalReservation(state) {
            state.isReservationPersonal = false
        }
    }
})

export const { personalReservation, notPersonalReservation } = reservationPersonal.actions

export const useReservationPersonal = () =>
    useSelector((state: RootState) => state.reservationPersonal.isReservationPersonal)

export default reservationPersonal.reducer
