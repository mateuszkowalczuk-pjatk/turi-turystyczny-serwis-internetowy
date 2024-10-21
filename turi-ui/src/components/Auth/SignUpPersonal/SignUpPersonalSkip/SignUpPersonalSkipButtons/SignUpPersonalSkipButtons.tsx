import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import {GreenButton } from '../../../../Controls/Button'
import styles from './SignUpPersonalSkipButtons.module.css'

const SignUpPersonalSkipButtons = ({ onClick }: { onClick: () => void }) => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToHome = () => {
        navigate('/');
    }

    return (
        <div className={styles.buttons}>
            <GreenButton
                text={t('signup-personal.back')}
                onClick={onClick}
            />
            <GreenButton
                text={t('signup-personal.continue')}
                onClick={navigateToHome}
            />
        </div>
    )
}

export default SignUpPersonalSkipButtons