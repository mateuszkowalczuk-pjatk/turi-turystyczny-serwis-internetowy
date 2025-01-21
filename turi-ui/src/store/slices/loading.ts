import { createSlice } from '@reduxjs/toolkit'
import { LOADING_SLICE } from '../types.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../store.ts'

interface LoadingState {
    isLoading: boolean
}

const initialState: LoadingState = {
    isLoading: false
}

const loading = createSlice({
    name: LOADING_SLICE,
    initialState,
    reducers: {
        startLoading(state) {
            state.isLoading = true
        },
        stopLoading(state) {
            state.isLoading = false
        }
    }
})

export const { startLoading, stopLoading } = loading.actions

export const useLoading = () => useSelector((state: RootState) => state.loading.isLoading)

export default loading.reducer
