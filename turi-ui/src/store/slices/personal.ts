import { createSlice } from '@reduxjs/toolkit'
import { PERSONAL_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface PersonalState {
    isPersonal: boolean
}

const initialState: PersonalState = {
    isPersonal: false
}

const personal = createSlice({
    name: PERSONAL_SLICE,
    initialState,
    reducers: {
        personalization(state) {
            state.isPersonal = true
        },
        notPersonalization(state) {
            state.isPersonal = false
        }
    }
})

export const { personalization, notPersonalization } = personal.actions

export const usePersonal = () => useSelector((state: RootState) => state.personal.isPersonal)

export default personal.reducer
