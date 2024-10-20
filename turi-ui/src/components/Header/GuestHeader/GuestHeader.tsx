import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import Logo from '../../Logo'
import HeaderButtons from '../HeaderButtons'
import styles from './GuestHeader.module.css'

const GuestHeader = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToLogin = () => {
        navigate('/login')
    }

    const navigateToSignUp = () => {
        navigate('/signup')
    }


    return (
        <div className={styles.header}>
            <Logo />
            <HeaderButtons
                text={t('header.sign-in-button')}
                firstOnClick={navigateToLogin}
                secondOnClick={navigateToSignUp}
            />
        </div>
    )
}

export default GuestHeader;