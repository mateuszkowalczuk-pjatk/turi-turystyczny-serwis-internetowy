import { RootState } from "../../../store/store.ts";
import { useTranslation } from "react-i18next";
import { useNavigate } from 'react-router-dom'
import { GreyButton } from "../../Controls/Button";
import { useSelector } from "react-redux";
import HeaderWrapper from "../HeaderDropDown/HeaderWrapper";
import styles from './HeaderButtons.module.css'

const HeaderButtons = ({ text }: { text: string }) => {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

    const navigateToLogin = () => {
        navigate('/login');
    };

    const navigateToSignUp = () => {
        navigate('/signup');
    };

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={ text }
                onClick={navigateToLogin}
            />
            {!isAuthenticated ? (
                <GreyButton
                    text={t('header.sign-up-button')}
                    onClick={navigateToSignUp}
                />
            ) : (
                <HeaderWrapper />
            )}
        </div>
    )
}

export default HeaderButtons;