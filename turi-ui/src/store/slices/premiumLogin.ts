import { createSlice } from '@reduxjs/toolkit'
import { PREMIUM_LOGIN_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface PremiumLoginState {
    isPremiumLogin: boolean
}

const initialState: PremiumLoginState = {
    isPremiumLogin: false
}

const premiumLogin = createSlice({
    name: PREMIUM_LOGIN_SLICE,
    initialState,
    reducers: {
        loginPremium(state) {
            state.isPremiumLogin = true
        },
        notLoginPremium(state) {
            state.isPremiumLogin = false
        }
    }
})

export const { loginPremium, notLoginPremium } = premiumLogin.actions

export const usePremiumLogin = () => useSelector((state: RootState) => state.premiumLogin.isPremiumLogin)

export default premiumLogin.reducer
