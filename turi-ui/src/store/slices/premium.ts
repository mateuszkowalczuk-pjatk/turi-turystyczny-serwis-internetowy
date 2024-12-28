import { createSlice } from '@reduxjs/toolkit'
import { PREMIUM_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface PremiumState {
    isPremiumAccount: boolean
}

const initialState: PremiumState = {
    isPremiumAccount: false
}

const premium = createSlice({
    name: PREMIUM_SLICE,
    initialState,
    reducers: {
        premiumAccount(state) {
            state.isPremiumAccount = true
        },
        notPremiumAccount(state) {
            state.isPremiumAccount = false
        }
    }
})

export const { premiumAccount, notPremiumAccount } = premium.actions

export const usePremium = () => useSelector((state: RootState) => state.premium.isPremiumAccount)

export default premium.reducer
