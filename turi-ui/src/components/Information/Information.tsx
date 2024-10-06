import {useTranslation} from "react-i18next";
import styles from './Information.module.css';

const Information = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.information}>
            <div className={styles.texts}>
                <div className={styles.text}>
                    <p className={styles.header}>{t('information.header-text')}</p>
                </div>
                <div className={styles.text}>
                    <p className={styles.registration}>{t('information.sign-up-text')}</p>
                </div>
                <div className={styles.text}>
                    <p className={styles.premium}>{t('information.premium-account-text')}</p>
                </div>
            </div>
        </div>
    )
}

export default Information;