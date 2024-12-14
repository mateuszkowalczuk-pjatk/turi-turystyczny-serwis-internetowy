import { createSlice } from '@reduxjs/toolkit'
import { PREMIUM_LOGIN_SLICE } from '../types.ts'

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

export default premiumLogin.reducer
