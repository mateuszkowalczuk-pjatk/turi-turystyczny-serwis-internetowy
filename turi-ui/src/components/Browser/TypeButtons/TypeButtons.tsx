import { useTranslation } from "react-i18next";
import { GreyButton } from "../../Button";
import styles from './TypeButtons.module.css';

const TypeButtons = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('dashboard.all-button')}
            />
            <GreyButton
                text={t('dashboard.stay-button')}
            />
            <GreyButton
                text={t('dashboard.atraction-button')}
            />
        </div>
    )
}

export default TypeButtons;