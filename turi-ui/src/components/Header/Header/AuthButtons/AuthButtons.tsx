import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { GreyButton } from '../../../Button'
import styles from './AuthButtons.module.css'

const AuthButtons = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToLogin = () => {
        navigate('/login');
    };

    const navigateToSignUp = () => {
        navigate('/signup');
    };

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('header.sign-in-button')}
                onClick={navigateToLogin}
            />
            <GreyButton
                text={t('header.sign-up-button')}
                onClick={navigateToSignUp}
            />
        </div>
    )
}

export default AuthButtons;