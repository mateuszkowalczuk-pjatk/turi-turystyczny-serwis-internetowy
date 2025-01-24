import { createSlice } from '@reduxjs/toolkit'
import { PREMIUM_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface PremiumState {
    isPremium: boolean
}

const initialState: PremiumState = {
    isPremium: false
}

const premium = createSlice({
    name: PREMIUM_SLICE,
    initialState,
    reducers: {
        premiumAccount(state) {
            state.isPremium = true
        },
        notPremiumAccount(state) {
            state.isPremium = false
        }
    }
})

export const { premiumAccount, notPremiumAccount } = premium.actions

export const usePremium = () => useSelector((state: RootState) => state.premium.isPremium)

export default premium.reducer
