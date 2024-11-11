import { createSlice } from '@reduxjs/toolkit'
import { ACTIVATE_SLICE } from '../types.ts'

interface ActivateState {
    isActivation: boolean
}

const initialState: ActivateState = {
    isActivation: false
}

const activate = createSlice({
    name: ACTIVATE_SLICE,
    initialState,
    reducers: {
        activation(state) {
            state.isActivation = true
        },
        notActivation(state) {
            state.isActivation = false
        }
    }
})

export const { activation, notActivation } = activate.actions

export default activate.reducer
