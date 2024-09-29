import { BrowserRouter } from 'react-router-dom';
import AppRoutes from './routes/AppRoutes';
import './style.css'

const App = () => {
    return (
        <BrowserRouter>
            <AppRoutes />
        </BrowserRouter>
    )
}

export default App;