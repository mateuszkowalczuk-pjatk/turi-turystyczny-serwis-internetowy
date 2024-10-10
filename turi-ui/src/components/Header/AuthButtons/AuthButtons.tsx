import { useTranslation } from "react-i18next";
import { GreyButton } from "../../Button";
import styles from './AuthButtons.module.css'

const AuthButtons = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('header.sign-in-button')}
            />
            <GreyButton
                text={t('header.sign-up-button')}
            />
        </div>
    )
}

export default AuthButtons;