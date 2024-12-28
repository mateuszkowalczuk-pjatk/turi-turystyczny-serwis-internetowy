import { createSlice } from '@reduxjs/toolkit'
import { PERSONAL_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

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

export const usePersonal = () => useSelector((state: RootState) => state.personal.isPersonalization)

export default personal.reducer
