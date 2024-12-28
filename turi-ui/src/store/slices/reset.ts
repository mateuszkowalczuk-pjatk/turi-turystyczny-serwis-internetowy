import { createSlice } from '@reduxjs/toolkit'
import { RESET_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface ResetState {
    isResetPassword: boolean
}

const initialState: ResetState = {
    isResetPassword: false
}

const reset = createSlice({
    name: RESET_SLICE,
    initialState,
    reducers: {
        resetPassword(state) {
            state.isResetPassword = true
        },
        notResetPassword(state) {
            state.isResetPassword = false
        }
    }
})

export const { resetPassword, notResetPassword } = reset.actions

export const useReset = () => useSelector((state: RootState) => state.reset.isResetPassword)

export default reset.reducer
