import { useTranslation } from "react-i18next";
import { GreyButton } from "../../Button";
import styles from './TypeButtons.module.css';

const TypeButtons = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('home.dashboard.all-button')}
            />
            <GreyButton
                text={t('home.dashboard.stay-button')}
            />
            <GreyButton
                text={t('home.dashboard.attraction-button')}
            />
        </div>
    )
}

export default TypeButtons;