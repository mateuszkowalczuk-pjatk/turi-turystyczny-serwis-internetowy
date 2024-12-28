import { createSlice } from '@reduxjs/toolkit'
import { PREMIUM_BUY_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface PremiumBuyState {
    isPremiumBuy: boolean
}

const initialState: PremiumBuyState = {
    isPremiumBuy: false
}

const premiumBuy = createSlice({
    name: PREMIUM_BUY_SLICE,
    initialState,
    reducers: {
        buyPremium(state) {
            state.isPremiumBuy = true
        },
        notBuyPremium(state) {
            state.isPremiumBuy = false
        }
    }
})

export const { buyPremium, notBuyPremium } = premiumBuy.actions

export const usePremiumBuy = () => useSelector((state: RootState) => state.premiumBuy.isPremiumBuy)

export default premiumBuy.reducer
