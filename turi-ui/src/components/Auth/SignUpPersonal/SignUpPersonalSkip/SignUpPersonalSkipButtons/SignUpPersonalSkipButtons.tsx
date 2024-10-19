import { useTranslation } from 'react-i18next'
import { useNavigate } from "react-router-dom";
import { GreyButton } from '../../../../Controls/Button'
import styles from './SignUpPersonalSkipButtons.module.css'

const SignUpPersonalSkipButtons = ({ onClick }: { onClick: () => void }) => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToHome = () => {
        navigate('/');
    }

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('signup-personal.back')}
                onClick={onClick}
            />
            <GreyButton
                text={t('signup-personal.continue')}
                onClick={navigateToHome}
            />
        </div>
    )
}

export default SignUpPersonalSkipButtons;