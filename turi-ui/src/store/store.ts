import { configureStore } from '@reduxjs/toolkit'
import authReducer from './slices/auth.ts'
import activateReducer from './slices/activate.ts'
import personalReducer from './slices/personal.ts'
import resetReducer from './slices/reset.ts'

const store = configureStore({
    reducer: {
        auth: authReducer,
        activate: activateReducer,
        personal: personalReducer,
        reset: resetReducer
    }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
