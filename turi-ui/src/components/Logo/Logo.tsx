import { useNavigate } from 'react-router-dom'
import styles from './Logo.module.css'

const Logo = () => {
    const navigate = useNavigate();

    const navigateToHome = () => {
        navigate('/');
    };

    return (
        <p
            className={styles.logo}
            onClick={navigateToHome}
        >
            Turi
        </p>
    )
}

export default Logo;