import { useTranslation } from "react-i18next";
import { useNavigate } from 'react-router-dom'
import { GreyButton } from "../../Button";
import styles from './AuthButtons.module.css'

const AuthButtons = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToSignup = () => {
        navigate('/signup');
    };

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('header.sign-in-button')}
            />
            <GreyButton
                text={t('header.sign-up-button')}
                onClick={navigateToSignup}
            />
        </div>
    )
}

export default AuthButtons;