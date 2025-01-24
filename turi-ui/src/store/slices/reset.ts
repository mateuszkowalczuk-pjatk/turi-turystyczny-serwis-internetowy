import { createSlice } from '@reduxjs/toolkit'
import { RESET_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface ResetState {
    isReset: boolean
}

const initialState: ResetState = {
    isReset: false
}

const reset = createSlice({
    name: RESET_SLICE,
    initialState,
    reducers: {
        resetPassword(state) {
            state.isReset = true
        },
        notResetPassword(state) {
            state.isReset = false
        }
    }
})

export const { resetPassword, notResetPassword } = reset.actions

export const useReset = () => useSelector((state: RootState) => state.reset.isReset)

export default reset.reducer
