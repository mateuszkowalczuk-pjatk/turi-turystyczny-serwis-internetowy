import { useNavigate } from 'react-router-dom'
import styles from './HeaderLogo.module.css'

const HeaderLogo = () => {
    const navigate = useNavigate()

    return (
        <p
            className={styles.logo}
            onClick={() => navigate('/')}
        >
            Turi
        </p>
    )
}

export default HeaderLogo
