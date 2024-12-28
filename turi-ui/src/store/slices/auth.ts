import { createSlice } from '@reduxjs/toolkit'
import { AUTH_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

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

export const useAuthenticated = () => useSelector((state: RootState) => state.auth.isAuthenticated)

export default auth.reducer
