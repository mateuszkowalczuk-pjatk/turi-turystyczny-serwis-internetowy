import { useNavigate } from 'react-router-dom'
import styles from './HeaderLogo.module.css'

const HeaderLogo = () => {
    const navigate = useNavigate()

    const navigateToHome = () => {
        navigate('/')
    }

    return (
        <p
            className={styles.logo}
            onClick={navigateToHome}
        >
            Turi
        </p>
    )
}

export default HeaderLogo
