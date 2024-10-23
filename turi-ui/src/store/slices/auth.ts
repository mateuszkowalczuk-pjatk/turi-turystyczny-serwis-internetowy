import { createSlice, PayloadAction } from '@reduxjs/toolkit'
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
        },
        setAuthState(state, action: PayloadAction<boolean>) {
            state.isAuthenticated = action.payload
        }
    }
})

export const { login, logout, setAuthState } = auth.actions

export default auth.reducer
