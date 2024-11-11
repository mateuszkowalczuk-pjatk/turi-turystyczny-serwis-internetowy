import { createSlice } from '@reduxjs/toolkit'
import { AUTH_SLICE } from '../types.ts'

interface AuthState {
    isAuthenticated: boolean
}

const initialState: AuthState = {
    isAuthenticated: false
}

const auth = createSlice({
    name: AUTH_SLICE,
    initialState,
    reducers: {
        login(state) {
            state.isAuthenticated = true
        },
        logout(state) {
            state.isAuthenticated = false
        }
    }
})

export const { login, logout } = auth.actions

export default auth.reducer
