import { createSlice } from '@reduxjs/toolkit'
import { PERSONAL_SLICE } from '../types.ts'

interface PersonalState {
    isPersonalization: boolean
}

const initialState: PersonalState = {
    isPersonalization: false
}

const personal = createSlice({
    name: PERSONAL_SLICE,
    initialState,
    reducers: {
        personalization(state) {
            state.isPersonalization = true
        },
        notPersonalization(state) {
            state.isPersonalization = false
        }
    }
})

export const { personalization, notPersonalization } = personal.actions

export default personal.reducer
