import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux'
import AppRoutes from './routes/AppRoutes'
import store from './store/store.ts'
import './style.css'

const App = () => {
    return (
        <Provider store={store}>
            <BrowserRouter>
                <AppRoutes />
            </BrowserRouter>
        </Provider>
    )
}

export default App
