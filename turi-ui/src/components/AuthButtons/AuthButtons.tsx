import {useTranslation} from "react-i18next";
import Button from "../Button";
import styles from './AuthButtons.module.css'

const AuthButtons = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <Button
                text={t('header.sign-in-button')}
            />
            <Button
                text={t('header.sign-up-button')}
            />
        </div>
    )
}

export default AuthButtons;